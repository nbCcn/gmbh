
package com.guming.service.arrangement.impl;


import com.guming.arrangement.dto.ArrangementMoveDto;
import com.guming.arrangement.dto.ArrangementReMoveDto;
import com.guming.arrangement.dto.ShopArrangementQueryDto;
import com.guming.arrangement.dto.query.ArrangementQueryDto;
import com.guming.arrangement.entity.PlansArrangement;
import com.guming.arrangement.vo.ArrangementDateVo;
import com.guming.arrangement.vo.ArrangementDayVo;
import com.guming.arrangement.vo.ArrangementVo;
import com.guming.arrangement.vo.ShopArrangementVo;
import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.utils.DateUtil;
import com.guming.dao.arrangement.ArrangementRepository;
import com.guming.dao.plans.PathRepository;
import com.guming.plans.entity.Pathshop;
import com.guming.plans.entity.PlansPath;
import com.guming.service.arrangement.ArrangementService;
import com.guming.service.setups.SetupsService;
import com.guming.setups.vo.BasicConfigVo;
import com.guming.tagline.entity.TagLine;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/2 10:13
 */

@Service
@SuppressWarnings("all")
public class ArrangementServiceImpl extends BaseServiceImpl implements ArrangementService {

    @Autowired
    private SetupsService setupsService;

    @Autowired
    private ArrangementRepository arrangementRepository;

    @Autowired
    private PathRepository pathRepository;

    @Override
    protected BaseRepository getRepository() {
        return this.arrangementRepository;
    }

    /**
     * 路线安排查询
     *
     * @param arrangementQueryDto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseParam find(ArrangementQueryDto arrangementQueryDto) {
        if (StringUtils.isEmpty(arrangementQueryDto.getDateStr())) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ARRANGEMENT_CLASS_DATE_EMPTY);
        }
        String dateStr = arrangementQueryDto.getDateStr();
        String[] split = dateStr.split("-");
        List<Object[]> objectList = arrangementRepository.find(Integer.parseInt(split[0]), Integer.parseInt(split[1]), arrangementQueryDto.getTagwareHouseId());
        ArrangementDateVo arrangementDateVo = new ArrangementDateVo();
        arrangementDateVo.setYearStr(split[0]);
        arrangementDateVo.setMonthStr(split[1]);
        Set set = new HashSet();
        List list = new ArrayList();
        for (Object[] object : objectList) {
            split = object[3].toString().split("-");
            list.add(split[2]);
        }
        set.addAll(list);
        List<ArrangementDayVo> arrangementDayVosList = new ArrayList<>();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            ArrangementDayVo arrangementDayVo = new ArrangementDayVo();
            arrangementDayVo.setDay(it.next());
            arrangementDayVosList.add(arrangementDayVo);
        }
        for (ArrangementDayVo arrangementDayVo : arrangementDayVosList) {
            List<ArrangementVo> arrangementVoList = new ArrayList<>();
            for (Object[] object : objectList) {
                split = object[3].toString().split("-");
                if (split[2].equals(arrangementDayVo.getDay())) {
                    ArrangementVo arrangementVo = new ArrangementVo();
                    arrangementVo.setPathId(Long.parseLong(object[0].toString()));
                    arrangementVo.setArrangementId(Long.parseLong(object[1].toString()));
                    arrangementVo.setTagLineName(object[2].toString());
                    arrangementVoList.add(arrangementVo);
                }
            }
            arrangementDayVo.setArrangementVoList(arrangementVoList);
        }
        arrangementDateVo.setArrangementDayVoList(arrangementDayVosList);
        return ResponseParam.success(arrangementDateVo);
    }


    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public PlansArrangement getSendTimePlansArrangement(Long shopId) {
        //查詢出時間段
        Date currentDate = DateUtil.parseDate(DateUtil.formatDate(new Date()));
        BasicConfigVo basicConfigVo = setupsService.findBasicConfig().getResult();
        Date startDate = DateUtil.getWarpDay(currentDate, basicConfigVo.getAdvanceEndDay());
        Date endDate = DateUtil.getWarpDay(currentDate, basicConfigVo.getAdvanceStartDay());
        List<PlansArrangement> plansArrangementList = arrangementRepository.findAllByShopIdAndDay(shopId, startDate, endDate);
        if (plansArrangementList != null && !plansArrangementList.isEmpty()) {
            return plansArrangementList.get(0);
        }
        return null;
    }

    /**
     * 移入
     *
     * @param arrangementMoveDto
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResponseParam move(ArrangementMoveDto arrangementMoveDto) {
        if (arrangementMoveDto.getPathId() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERR_VALIDATION_PATH_CLASS_ID_EMPTY);
        }
        ResponseParam<BasicConfigVo> basicConfig = setupsService.findBasicConfig();
        BasicConfigVo result = basicConfig.getResult();
        Integer startDay = result.getAdvanceStartDay();

        String[] split = arrangementMoveDto.getDayStr().toString().split("-");
        List<Object> count = arrangementRepository.findPathCount(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]) - startDay, Integer.parseInt(split[2]), arrangementMoveDto.getPathId());
        if (count.size() >= 1) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ARRANGEMENT_TIME_INTERVAL_NOT_ACCORD);
        }


        PlansArrangement arrangement = new PlansArrangement();
        arrangement.setDay(DateUtil.parseDate(arrangementMoveDto.getDayStr()));
        arrangement.setCreatedTime(new Date());
        arrangement.setUpdatedTime(new Date());


        Long pathId = arrangementMoveDto.getPathId();
        PlansPath path = pathRepository.findOne(pathId);
        List<PlansPath> pathList = new ArrayList<>();
        pathList.add(path);
        arrangement.setPlansPathList(pathList);
        PlansArrangement plansArrangement = arrangementRepository.save(arrangement);


        ArrangementVo arrangementVo = new ArrangementVo();
        arrangementVo.setArrangementId(plansArrangement.getId());
        arrangementVo.setPathId(path.getId());
        return ResponseParam.success(arrangementVo, i18nHandler(ErrorMsgConstants.OPTION_ADD_SUCCESS_MSG));
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResponseParam remove(ArrangementReMoveDto arrangementReMoveDto) {
        if (arrangementReMoveDto.getArrangementId() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ARRANGEMENT_CLASS_ID_EMPTY);
        }
        if (arrangementReMoveDto.getPathId() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERR_VALIDATION_PATH_CLASS_ID_EMPTY);
        }
        PlansArrangement arrangement = arrangementRepository.findOne(arrangementReMoveDto.getArrangementId());
        if (arrangement == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ARRANGEMENT_CLASS_EMPTY);
        }
        arrangementRepository.deleteById(arrangementReMoveDto.getArrangementId(), arrangementReMoveDto.getPathId());
        return getSuccessDeleteResult();
    }

    @Override
    @Transactional(readOnly = true)
    public void export(String dayStr, Long tagwareHouseId, HttpServletResponse response) throws Exception {
        XSSFWorkbook wb = null;
        List<PlansArrangement> arrangementList = arrangementRepository.findByDay(DateUtil.parseDate(DateUtil.getFirstDay(dayStr)), DateUtil.parseDate(DateUtil.getLastDay(dayStr)), tagwareHouseId);
        // 读取excel模板
        // 服务器路径  /usr/local/book/templates
        // 本地路径  classpath:templates
        wb = new XSSFWorkbook(ResourceUtils.getFile("/usr/local/book/templates/arrangement.xlsx"));
        // 读取了模板内所有sheet内容
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = null;
        Integer week = null;
        XSSFCell cell = null;
        for (PlansArrangement arrangement : arrangementList) {
            StringBuilder names = new StringBuilder();
            List<PlansPath> plansPathList = arrangement.getPlansPathList();
            Integer day = DateUtil.weekOfMonth(arrangement.getDay().toString());
            if (DateUtil.dateToWeek(arrangement.getDay().toString()) == 6) {
                day--;
            }
            for (PlansPath path : plansPathList) {
                TagLine tagLine = path.getTagLine();
                names.append(tagLine.getName() + "\n");
                switch (day) {
                    case 1:
                        row = sheet.getRow(2);
                        if (row == null) {
                            sheet.createRow(2);
                        }
                        week = DateUtil.dateToWeek(arrangement.getDay().toString());
                        cell = row.getCell(week);
                        if (null == cell) {
                            cell = row.createCell(week);
                        }
                        cell.setCellValue(cell.getStringCellValue() + names);
                        break;
                    case 2:
                        row = sheet.getRow(3);
                        if (row == null) {
                            sheet.createRow(3);
                        }
                        week = DateUtil.dateToWeek(arrangement.getDay().toString());
                        cell = row.getCell(week);
                        if (null == cell) {
                            cell = row.createCell(week);
                        }
                        cell.setCellValue(cell.getStringCellValue() + names);
                        break;
                    case 3:
                        row = sheet.getRow(4);
                        if (row == null) {
                            sheet.createRow(4);
                        }
                        week = DateUtil.dateToWeek(arrangement.getDay().toString());
                        cell = row.getCell(week);
                        if (null == cell) {
                            cell = row.createCell(week);
                        }
                        cell.setCellValue(cell.getStringCellValue() + names);
                        break;
                    case 4:
                        row = sheet.getRow(5);
                        if (row == null) {
                            sheet.createRow(5);
                        }
                        week = DateUtil.dateToWeek(arrangement.getDay().toString());
                        cell = row.getCell(week);
                        if (null == cell) {
                            cell = row.createCell(week);
                        }
                        cell.setCellValue(cell.getStringCellValue() + names);
                        break;
                    case 5:
                        row = sheet.getRow(6);
                        if (row == null) {
                            sheet.createRow(6);
                        }
                        week = DateUtil.dateToWeek(arrangement.getDay().toString());
                        cell = row.getCell(week);
                        if (null == cell) {
                            cell = row.createCell(week);
                        }
                        cell.setCellValue(cell.getStringCellValue() + names);
                        break;
                    case 6:
                        row = sheet.getRow(7);
                        if (row == null) {
                            sheet.createRow(7);
                        }
                        week = DateUtil.dateToWeek(arrangement.getDay().toString());
                        cell = row.getCell(week);
                        if (null == cell) {
                            cell = row.createCell(week);
                        }
                        cell.setCellValue(cell.getStringCellValue() + names);
                        break;
                    default:
                        break;
                }
            }
        }

        Set<PlansPath> pathSet = new HashSet<>();

        for (PlansArrangement plansArrangement : arrangementList) {
            List<PlansPath> pathList = plansArrangement.getPlansPathList();
            for (PlansPath path : pathList) {
                pathSet.add(path);
            }
        }

        Integer index = 8;
        for (PlansPath path : pathSet) {
            TagLine tagLine = path.getTagLine();

            row = sheet.getRow(index);
            if (row == null) {
                sheet.createRow(index);
            }
            cell = row.getCell(0);
            if (null == cell) {
                cell = row.createCell(0);
            }
            cell.setCellValue(tagLine.getName());
            List<Pathshop> pathshopList = path.getPathshopList();
            for (Pathshop pathshop : pathshopList) {
                cell = row.getCell(1);
                if (null == cell) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(cell.getStringCellValue() + pathshop.getShopsShop().getName() + "-->");
            }
            cell = row.getCell(6);
            if (null == cell) {
                cell = row.createCell(6);
            }
            cell.setCellValue(pathshopList.size());
            index++;
        }

        String fileName = "物流排期路由表";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
        ServletOutputStream sout = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(sout);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new ErrorMsgException(ErrorMsgConstants.SYSTEM_FAILED_MSG);
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
    }

    /**
     * 店铺查询送货安排
     *
     * @param shopArrangementQueryDto
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResponseParam findByShop(ShopArrangementQueryDto shopArrangementQueryDto) {
        try {
            String dayStr = shopArrangementQueryDto.getDayStr();
            String[] split = dayStr.split("-");
            String yearStr = split[0];
            String monthStr = split[1];
            List<Object[]> resultList = arrangementRepository.findByShop(yearStr, monthStr, shopArrangementQueryDto.getShopId());

            List<ShopArrangementVo> shopArrangementVoList = new ArrayList<>();
            for (Object[] object : resultList) {
                ShopArrangementVo shopArrangementVo = new ShopArrangementVo();

                shopArrangementVo.setDateStr(object[0].toString());
                shopArrangementVo.setWeekStr(DateUtil.stringToWeek(object[0].toString()));
                shopArrangementVo.setTagLineName(object[1].toString());
                shopArrangementVo.setManager(object[2].toString());
                shopArrangementVo.setPhone(object[3].toString());

                shopArrangementVoList.add(shopArrangementVo);
            }

            return ResponseParam.success(shopArrangementVoList);

        } catch (Exception e) {
            throw new ErrorMsgException(ErrorMsgConstants.SYSTEM_FAILED_MSG);
        }

    }


}
