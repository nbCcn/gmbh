package com.guming.service.plans.impl;

import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.ShopStatus;
import com.guming.common.utils.ExcelUtil;
import com.guming.dao.plans.PathRepository;
import com.guming.dao.shops.ShopRepository;
import com.guming.dao.tagwareHouse.TagwareHouseRepository;
import com.guming.plans.dto.PathAddDto;
import com.guming.plans.dto.PathShopDto;
import com.guming.plans.dto.query.PathQuery;
import com.guming.plans.dto.query.ShopFuzzyQuery;
import com.guming.plans.entity.Pathshop;
import com.guming.plans.entity.PlansPath;
import com.guming.plans.vo.PathExportVo;
import com.guming.plans.vo.PathShopVo;
import com.guming.plans.vo.PathVo;
import com.guming.plans.vo.ShopNameVo;
import com.guming.service.plans.PathService;
import com.guming.shops.entitiy.ShopsShop;
import com.guming.tagline.entity.TagLine;
import com.guming.tagwareHouse.entity.TagwareHouse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/24 11:19
 */
@Service
@SuppressWarnings("all")
public class PathServiceImpl extends BaseServiceImpl implements PathService {


    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private TagwareHouseRepository tagwareHouseRepository;

    @Override
    protected BaseRepository getRepository() {
        return this.pathRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseParam<?> findLine(PathQuery pathQuery) {
        if (pathQuery.getTagwareHouseId() == 0) {
            List<PathVo> pathVoList = new ArrayList<>();
            List<TagwareHouse> tagwareHouseList = tagwareHouseRepository.findAll();
            for (TagwareHouse tagwareHouse : tagwareHouseList) {
                List<TagLine> tagLineList = tagwareHouse.getTagLines();
                for (TagLine tagLine : tagLineList) {
                    PlansPath path = tagLine.getPlansPath();
                    PathVo pathVo = new PathVo();
                    pathVo.setTagwareHouseId(tagwareHouse.getId());
                    pathVo.setTagLineId(tagLine.getId());
                    pathVo.setTagLineName(tagLine.getName());
                    if (path != null) {
                        pathVo.setPathId(path.getId());
                        pathVo.setCount(path.getPathshopList().size());
                    } else {
                        pathVo.setCount(0);
                    }
                    pathVoList.add(pathVo);
                }
            }
            List<Object[]> noTagLine = pathRepository.findAllNoLine(0);
            PathVo pathVo = new PathVo(0L, 0L, 0L, "暂无安排");
            pathVo.setCount(noTagLine.size());
            pathVoList.add(0, pathVo);
            return ResponseParam.success(pathVoList);
        } else {
            List<PathVo> pathVoList = pathRepository.findTagLine(pathQuery.getTagwareHouseId());
            for (PathVo pathVo : pathVoList) {
                Long id = pathVo.getPathId();
                PlansPath plansPath = pathRepository.findOne(id);
                pathVo.setCount(plansPath.getPathshopList().size());
            }
            List<Object> noTagLine = pathRepository.findNoTagLine(pathQuery.getTagwareHouseId(), 0, pathQuery.getTagwareHouseId());
            PathVo pathVo = new PathVo(pathQuery.getTagwareHouseId(), 0L, 0L, "暂无安排");
            pathVo.setCount(noTagLine.size());
            pathVoList.add(0, pathVo);
            return ResponseParam.success(pathVoList);
        }
    }

    /**
     * @param pathQuery:ID path_ID
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseParam<?> findShop(PathQuery pathQuery) {
        if (pathQuery.getTagwareHouseId() == 0 && pathQuery.getPathId() == 0) {
            List<Object[]> allNoLineShop = pathRepository.findAllNoLineShop(ShopStatus.CLOSE.getCode());
            List<PathShopVo> pathShopVoList = new ArrayList<>();
            for (Object[] object : allNoLineShop) {
                String tagwareHouseName = object[0].toString();
                Long id = Long.parseLong(object[1].toString());
                String name = object[2].toString();
                String code = object[3].toString();
                String address = object[4].toString();
                String contact = object[5].toString();
                String phone = object[6].toString();
                Integer status = Integer.parseInt(object[7].toString());
                Double lng = Double.parseDouble(object[8].toString());
                Double lat = Double.parseDouble(object[9].toString());
                Long tagwareHouseId = Long.parseLong(object[10].toString());
                String province = object[11].toString();
                String city = object[12].toString();
                String district = object[13].toString();
                PathShopVo pathShopVo = new PathShopVo();
                pathShopVo.setTagwareHouseName(tagwareHouseName);
                pathShopVo.setTagwareHouseId(tagwareHouseId);
                pathShopVo.setShopId(id);
                pathShopVo.setName(name);
                pathShopVo.setCode(code);
                pathShopVo.setAddress(province + city + district + address);
                pathShopVo.setContact(contact);
                pathShopVo.setPhone(phone);
                pathShopVo.setStatus(status);
                pathShopVo.setStatusStr(ShopStatus.getShopStatus(status).getName());
                pathShopVo.setLng(lng);
                pathShopVo.setLat(lat);
                pathShopVo.setPathId(pathQuery.getPathId());
                pathShopVo.setTagLineName("暂未安排路线");
                pathShopVoList.add(pathShopVo);
            }
            return ResponseParam.success(pathShopVoList);
        }
        if (pathQuery.getPathId() != null) {
            if (pathQuery.getPathId() == 0) {
                List<Object[]> noTagLineShop = pathRepository.findNoTagLineShop(pathQuery.getTagwareHouseId(), ShopStatus.CLOSE.getCode(), pathQuery.getTagwareHouseId());
                List<PathShopVo> pathShopVoList = new ArrayList<>();
                for (Object[] object : noTagLineShop) {
                    String tagwareHouseName = object[0].toString();
                    Long id = Long.parseLong(object[1].toString());
                    String name = object[2].toString();
                    String code = object[3].toString();
                    String address = object[4].toString();
                    String contact = object[5].toString();
                    String phone = object[6].toString();
                    Integer status = Integer.parseInt(object[7].toString());
                    Double lng = Double.parseDouble(object[8].toString());
                    Double lat = Double.parseDouble(object[9].toString());
                    Long tagwareHouseId = Long.parseLong(object[10].toString());
                    String province = object[11].toString();
                    String city = object[12].toString();
                    String district = object[13].toString();
                    PathShopVo pathShopVo = new PathShopVo();
                    pathShopVo.setTagwareHouseName(tagwareHouseName);
                    pathShopVo.setTagwareHouseId(tagwareHouseId);
                    pathShopVo.setShopId(id);
                    pathShopVo.setName(name);
                    pathShopVo.setCode(code);
                    pathShopVo.setAddress(province + city + district + address);
                    pathShopVo.setContact(contact);
                    pathShopVo.setPhone(phone);
                    pathShopVo.setStatus(status);
                    pathShopVo.setStatusStr(ShopStatus.getShopStatus(status).getName());
                    pathShopVo.setLng(lng);
                    pathShopVo.setLat(lat);
                    pathShopVo.setPathId(pathQuery.getPathId());
                    pathShopVo.setTagLineName("暂未安排路线");
                    pathShopVoList.add(pathShopVo);
                }
                return ResponseParam.success(pathShopVoList);
            } else {
                PlansPath plansPath = pathRepository.findOne(pathQuery.getPathId());
                TagLine tagLine = plansPath.getTagLine();
                TagwareHouse tagwareHouse = tagLine.getTagwareHouse();
                List<Pathshop> pathshopList = plansPath.getPathshopList();
                List<ShopsShop> shopList = new ArrayList<>();
                for (Pathshop pathshop : pathshopList) {
                    if (pathshop.getShopsShop().getStatus() != 0) {
                        ShopsShop shopsShop = pathshop.getShopsShop();
                        shopList.add(shopsShop);
                    }
                }
                List<PathShopVo> pathShopVoList = new ArrayList<>();
                for (ShopsShop shop : shopList) {
                    PathShopVo pathShopVo = new PathShopVo();
                    BeanUtils.copyProperties(shop, pathShopVo);
                    pathShopVo.setTagwareHouseName(tagwareHouse.getName());
                    pathShopVo.setTagwareHouseId(tagwareHouse.getId());
                    pathShopVo.setPathId(pathQuery.getPathId());
                    pathShopVo.setTagLineName(tagLine.getName());
                    pathShopVo.setShopId(shop.getId());
                    pathShopVo.setStatusStr(ShopStatus.getShopStatus(shop.getStatus()).getName());
                    pathShopVoList.add(pathShopVo);
                }
                return ResponseParam.success(pathShopVoList);
            }
        }
        return ResponseParam.success(null);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResponseParam<?> add(PathAddDto pathAddDto) {
        List<PathShopDto> pathShopDtos = pathAddDto.getPathShopDtos();
        for (PathShopDto pathShopDto : pathShopDtos) {
            if (pathAddDto.getNewPathId() != pathShopDto.getOldPathId()) {
                if (pathShopDto.getOldPathId() == 0) {
                    PlansPath path = pathRepository.findOne(pathAddDto.getNewPathId());
                    Long shopId = pathShopDto.getShopId();
                    ShopsShop shop = shopRepository.findOne(shopId);
                    List<Pathshop> pathshopList = new ArrayList<>();
                    Pathshop pathshop = new Pathshop();
                    pathshop.setShopsShop(shop);
                    pathshop.setPlansPath(path);
                    pathshop.setPathId(path.getId());
                    pathshop.setShopId(shop.getId());
                    pathshopList.add(pathshop);
                    path.setPathshopList(pathshopList);
                    pathRepository.save(path);
                } else {
                    if (pathAddDto.getNewPathId() == 0) {
                        pathRepository.removeToNoLine(pathShopDto.getShopId(), pathShopDto.getOldPathId());
                    } else {
                        pathRepository.removeToNoLine(pathShopDto.getShopId(), pathShopDto.getOldPathId());
                        PlansPath path = pathRepository.findOne(pathAddDto.getNewPathId());
                        Long shopId = pathShopDto.getShopId();
                        ShopsShop shop = shopRepository.findOne(shopId);
                        List<Pathshop> pathshopList = new ArrayList<>();
                        Pathshop pathshop = new Pathshop();
                        pathshop.setShopsShop(shop);
                        pathshop.setPlansPath(path);
                        pathshop.setPathId(path.getId());
                        pathshop.setShopId(shop.getId());
                        pathshopList.add(pathshop);
                        path.setPathshopList(pathshopList);
                        pathRepository.save(path);
                    }
                }
            }
        }
        return getSuccessAddResult();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseParam fuzzyFind(ShopFuzzyQuery shopFuzzyQuery) {
        Specification<ShopsShop> specification = new Specification<ShopsShop>() {
            @Override
            public Predicate toPredicate(Root<ShopsShop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(shopFuzzyQuery.getShopName())) {
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + shopFuzzyQuery.getShopName() + "%"));
                }
                list.add(criteriaBuilder.equal(root.get("isDeleted").as(Boolean.class), false));
                list.add(criteriaBuilder.notEqual(root.get("status").as(Integer.class), 0));
                Predicate[] predicates = new Predicate[list.size()];
                return criteriaQuery.where(list.toArray(predicates)).getRestriction();
            }
        };
        List<ShopsShop> shopsShops = shopRepository.findAll(specification);
        List<ShopNameVo> shopNameVoList = new ArrayList<>();
        for (ShopsShop shop : shopsShops) {
            ShopNameVo shopNameVo = new ShopNameVo();
            shopNameVo.setShopName(shop.getName());
            shopNameVoList.add(shopNameVo);
        }
        return ResponseParam.success(shopNameVoList);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseParam findShopbyName(ShopFuzzyQuery shopFuzzyQuery) {
        List<PathShopVo> pathShopVoList = new ArrayList<>();
        List<Object[]> list = pathRepository.findByShopName(shopFuzzyQuery.getShopName());
        if (list.size() != 0) {
            for (Object[] object : list) {
                String tagwareHouseName = object[0].toString();
                Long pathId = Long.parseLong(object[1].toString());
                String tagLineName = object[2].toString();
                Long shopId = Long.parseLong(object[3].toString());
                String shopName = object[4].toString();
                String shopCode = object[5].toString();
                String address = object[6].toString();
                String contact = object[7].toString();
                String phone = object[8].toString();
                Integer status = Integer.parseInt(object[9].toString());
                Double lng = Double.parseDouble(object[10].toString());
                Double lat = Double.parseDouble(object[11].toString());
                Long tagwareHouseId = Long.parseLong(object[12].toString());
                String province = object[13].toString();
                String city = object[14].toString();
                String district = object[15].toString();
                PathShopVo pathShopVo = new PathShopVo();
                pathShopVo.setTagwareHouseId(tagwareHouseId);
                pathShopVo.setTagwareHouseName(tagwareHouseName);
                pathShopVo.setPathId(pathId);
                pathShopVo.setTagLineName(tagLineName);
                pathShopVo.setShopId(shopId);
                pathShopVo.setName(shopName);
                pathShopVo.setCode(shopCode);
                pathShopVo.setAddress(province + city + district + address);
                pathShopVo.setContact(contact);
                pathShopVo.setPhone(phone);
                pathShopVo.setStatus(status);
                pathShopVo.setStatusStr(ShopStatus.getShopStatus(status).getName());
                pathShopVo.setLng(lng);
                pathShopVo.setLat(lat);
                pathShopVoList.add(pathShopVo);
            }
        }
        list = pathRepository.findNoLineShopByName(shopFuzzyQuery.getShopName(), shopFuzzyQuery.getShopName());
        if (list.size() != 0) {
            for (Object[] object : list) {
                Long tagwareHouseId = Long.parseLong(object[0].toString());
                String tagwareHouseName = object[1].toString();
                Long shopId = Long.parseLong(object[2].toString());
                String shopName = object[3].toString();
                String address = object[4].toString();
                String shopCode = object[5].toString();
                String contact = object[6].toString();
                String phone = object[7].toString();
                Double lng = Double.parseDouble(object[8].toString());
                Double lat = Double.parseDouble(object[9].toString());
                Integer status = Integer.parseInt(object[10].toString());
                String province = object[11].toString();
                String city = object[12].toString();
                String district = object[13].toString();
                PathShopVo pathShopVo = new PathShopVo();
                pathShopVo.setTagwareHouseId(tagwareHouseId);
                pathShopVo.setTagwareHouseName(tagwareHouseName);
                pathShopVo.setPathId(0L);
                pathShopVo.setTagLineName("暂无安排");
                pathShopVo.setShopId(shopId);
                pathShopVo.setName(shopName);
                pathShopVo.setCode(shopCode);
                pathShopVo.setAddress(province + city + district + address);
                pathShopVo.setContact(contact);
                pathShopVo.setPhone(phone);
                pathShopVo.setStatus(status);
                pathShopVo.setStatusStr(ShopStatus.getShopStatus(status).getName());
                pathShopVo.setLng(lng);
                pathShopVo.setLat(lat);
                pathShopVoList.add(pathShopVo);
            }
        }
        return ResponseParam.success(pathShopVoList);
    }


    /**
     * 导出
     *
     * @param tagwareHouseId
     */
    @Override
    @Transactional(readOnly = true)
    public void export(Long tagwareHouseId, HttpServletResponse response) {
        List<PathExportVo> pathExportVoList = new ArrayList<>();
        if (tagwareHouseId != 0) {
            List<Object[]> tagwareHouseList = pathRepository.findAllInLineShopById(tagwareHouseId);
            for (Object[] object : tagwareHouseList) {
                PathExportVo pathExportVo = new PathExportVo();
                pathExportVo.setTagwareHouseName(object[0].toString());
                pathExportVo.setTagLineName(object[1].toString());
                pathExportVo.setName(object[2].toString());
                pathExportVo.setCode(object[3].toString());
                pathExportVo.setAddress(object[4].toString() + object[5].toString() + object[6].toString() + object[7].toString());
                pathExportVo.setContact(object[8].toString());
                pathExportVo.setPhone(object[9].toString());
                pathExportVo.setStatusStr(ShopStatus.getShopStatus(Integer.parseInt(object[10].toString())).getName());
                pathExportVoList.add(pathExportVo);
            }
            tagwareHouseList = pathRepository.findAllNotInLineShopById(tagwareHouseId);
            for (Object[] objects : tagwareHouseList) {
                PathExportVo pathExportVo = new PathExportVo();
                pathExportVo.setTagwareHouseName(objects[0].toString());
                pathExportVo.setTagLineName("暂无安排");
                pathExportVo.setName(objects[1].toString());
                pathExportVo.setCode(objects[2].toString());
                pathExportVo.setAddress(objects[3].toString() + objects[4].toString() + objects[5].toString() + objects[6].toString());
                pathExportVo.setContact(objects[7].toString());
                pathExportVo.setPhone(objects[8].toString());
                pathExportVo.setStatusStr(ShopStatus.getShopStatus(Integer.parseInt(objects[9].toString())).getName());
                pathExportVoList.add(pathExportVo);
            }
        } else {
            List<Object[]> tagwareHouseList = pathRepository.findAllInLineShop();
            for (Object[] object : tagwareHouseList) {
                PathExportVo pathExportVo = new PathExportVo();
                pathExportVo.setTagwareHouseName(object[0].toString());
                pathExportVo.setTagLineName(object[1].toString());
                pathExportVo.setName(object[2].toString());
                pathExportVo.setCode(object[3].toString());
                pathExportVo.setAddress(object[4].toString() + object[5].toString() + object[6].toString() + object[7].toString());
                pathExportVo.setContact(object[8].toString());
                pathExportVo.setPhone(object[9].toString());
                pathExportVo.setStatusStr(ShopStatus.getShopStatus(Integer.parseInt(object[10].toString())).getName());
                pathExportVoList.add(pathExportVo);
            }
            tagwareHouseList = pathRepository.findAllNotInLineShop();
            for (Object[] objects : tagwareHouseList) {
                PathExportVo pathExportVo = new PathExportVo();
                pathExportVo.setTagwareHouseName(objects[0].toString());
                pathExportVo.setTagLineName("暂无安排");
                pathExportVo.setName(objects[1].toString());
                pathExportVo.setCode(objects[2].toString());
                pathExportVo.setAddress(objects[3].toString() + objects[4].toString() + objects[5].toString() + objects[6].toString());
                pathExportVo.setContact(objects[7].toString());
                pathExportVo.setPhone(objects[8].toString());
                pathExportVo.setStatusStr(ShopStatus.getShopStatus(Integer.parseInt(objects[9].toString())).getName());
                pathExportVoList.add(pathExportVo);
            }
        }


        //  服务器路径  /usr/local/book/templates
        //  本地路径   classpath:templates
        // 产生工作薄对象
        try {
            XSSFWorkbook wb = new XSSFWorkbook(ResourceUtils.getFile("/usr/local/book/templates/planshop.xlsx"));

            ExcelUtil<PathExportVo> excelUtil = new ExcelUtil<PathExportVo>(PathExportVo.class);

            excelUtil.exportExcel(pathExportVoList, wb, "路线规划表", response);// 导出
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
