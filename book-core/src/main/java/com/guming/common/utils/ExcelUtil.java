package com.guming.common.utils;

import com.guming.common.annotation.ExcelVOAttribute;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.exceptions.ErrorMsgException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Ccn
 * @Description: ExcelUtil工具类实现功能:
 * 导出时传入list<T>,即可实现导出为一个excel,其中每个对象Ｔ为Excel中的一条记录.
 * 导入时读取excel,得到的结果是一个list<T>.T是自己定义的对象.
 * 需要导出的实体对象只需简单配置注解就能实现灵活导出,通过注解您可以方便实现下面功能:
 * 1.实体属性配置了注解就能导出到excel中,每个属性都对应一列.
 * 2.列名称可以通过注解配置.
 * 3.导出到哪一列可以通过注解配置.
 * 4.鼠标移动到该列时提示信息可以通过注解配置.
 * 5.用注解设置只能下拉选择不能随意填写功能.
 * 6.用注解设置是否只导出标题而不导出内容,这在导出内容作为模板以供用户填写时比较实用.
 * @Date: 2018/5/9 18:25
 */
public class ExcelUtil<T> {

    Class<T> clazz;

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }


    /**
     * 是否是2003的excel，返回true是2003
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 是否是2007的excel，返回true是2007
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            return false;
        }
        return true;
    }


    /**
     * excel导入功能,只支持了2007版本,如果要导出2003版本的,请更换Workbook为HSSFWorkbook对象
     *
     * @param sheetName
     * @param wb
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public List<T> importExcel(String sheetName, Workbook wb) throws IllegalAccessException, InstantiationException {
        int maxCol = 0;
        List<T> list = new ArrayList<T>();
        Sheet sheet = wb.getSheet(sheetName);
        if (!sheetName.trim().equals("")) {
            // 如果指定sheet名,则取指定sheet中的内容.
            sheet = wb.getSheet(sheetName);
        }
        if (sheet == null) {
            // 如果传入的sheet名不存在则默认指向第1个sheet.
            sheet = wb.getSheetAt(0);
        }
        int rows = sheet.getPhysicalNumberOfRows();

        // 有数据时才处理
        if (rows > 0) {
            List<Field> allFields = getMappedFiled(clazz, null);

            // 定义一个map用于存放列的序号和field.
            Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
            for (Field field : allFields) {
                // 将有注解的field存放到map中.
                if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
                    ExcelVOAttribute attr = field
                            .getAnnotation(ExcelVOAttribute.class);
                    // 获得列号
                    int col = getExcelCol(attr.column());
                    maxCol = Math.max(col, maxCol);
                    // 设置类的私有字段属性可访问.
                    field.setAccessible(true);
                    fieldsMap.put(col, field);
                }
            }
            // 从第2行开始取数据,默认第一行是标题.
            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                int cellNum = maxCol;
                T entity = null;
                for (int j = 0; j < cellNum; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    int cellType = cell.getCellType();
                    String c = "";
                    if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                        c = String.valueOf(cell.getNumericCellValue());
                    } else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
                        c = String.valueOf(cell.getBooleanCellValue());
                    } else {
                        c = cell.getStringCellValue();
                    }
                    if (c == null || c.equals("")) {
                        continue;
                    }
                    // 如果不存在实例则新建.
                    entity = (entity == null ? clazz.newInstance() : entity);
                    // 从map中得到对应列的field.
                    Field field = fieldsMap.get(j);
                    if (field == null) {
                        continue;
                    }
                    // 取得类型,并根据对象类型设置值.
                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType) {
                        field.set(entity, String.valueOf(c));
                    } else if ((Integer.TYPE == fieldType)
                            || (Integer.class == fieldType)) {
                        field.set(entity, Integer.parseInt(c));
                    } else if ((Long.TYPE == fieldType)
                            || (Long.class == fieldType)) {
                        field.set(entity, Long.valueOf(c));
                    } else if ((Float.TYPE == fieldType)
                            || (Float.class == fieldType)) {
                        field.set(entity, Float.valueOf(c));
                    } else if ((Short.TYPE == fieldType)
                            || (Short.class == fieldType)) {
                        field.set(entity, Short.valueOf(c));
                    } else if ((Double.TYPE == fieldType)
                            || (Double.class == fieldType)) {
                        field.set(entity, Double.valueOf(c));
                    } else if (Character.TYPE == fieldType) {
                        if ((c != null) && (c.length() > 0)) {
                            field.set(entity, Character.valueOf(c.charAt(0)));
                        }
                    }
                }
                if (entity != null) {
                    list.add(entity);
                }
            }
        }
        return list;
    }

    /**
     * 导出操作对list数据源将其里面的数据导入到excel表单
     *
     * @param list 导出的数据集合
     * @param wb   工作薄对象
     */
    @SuppressWarnings(value = "all")
    public boolean exportExcel(List<T> list, XSSFWorkbook wb, String fileName, HttpServletResponse response) {
        try {

            List<Field> fields = getMappedFiled(clazz, null);

            XSSFSheet sheet = wb.getSheetAt(0);

            XSSFRow row = null;
            XSSFCell cell = null;
            row = sheet.createRow(0);// 产生一行
            // 写入各个字段的列头名称
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                ExcelVOAttribute attr = field
                        .getAnnotation(ExcelVOAttribute.class);
                int col = getExcelCol(attr.column());// 获得列号
                cell = row.createCell(col);// 创建列
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型
                cell.setCellValue(attr.name());// 写入列名
            }

            int startNo = 0;
            int endNo = list.size();
            // 写入各条记录,每条记录对应excel表中的一行
            for (int i = startNo; i < endNo; i++) {
                row = sheet.createRow(i + 1 - startNo);
                T vo = (T) list.get(i); // 得到导出对象.
                for (int j = 0; j < fields.size(); j++) {
                    Field field = fields.get(j);// 获得field.
                    field.setAccessible(true);// 设置实体类私有属性可访问
                    ExcelVOAttribute attr = field
                            .getAnnotation(ExcelVOAttribute.class);
                    try {
                        // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                        if (attr.isExport()) {
                            cell = row.createCell(getExcelCol(attr.column()));// 创建cell
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            cell.setCellValue(field.get(vo) == null ? "" : String.valueOf(field.get(vo)));// 如果数据存在就填入,不存在填入空格.
                        }
                    } catch (Exception e) {
                        throw new ErrorMsgException(ErrorMsgConstants.SYSTEM_FAILED_MSG);
                    }
                }
            }

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

        } catch (Exception e) {
            throw new ErrorMsgException(ErrorMsgConstants.SYSTEM_FAILED_MSG);
        }
        return true;
    }

    /**
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
     *
     * @param col
     */
    public static int getExcelCol(String col) {
        col = col.toUpperCase();
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
        int count = -1;
        char[] cs = col.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
        }
        return count;
    }


    /**
     * 得到实体类所有通过注解映射了数据表的字段
     *
     * @param fields
     * @return
     */
    private List<Field> getMappedFiled(Class clazz, List<Field> fields) {
        if (fields == null) {
            fields = new ArrayList<Field>();
        }

        // 得到所有定义字段
        Field[] allFields = clazz.getDeclaredFields();
        // 得到所有field并存放到一个list中.
        for (Field field : allFields) {
            if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
                fields.add(field);
            }
        }
        if (clazz.getSuperclass() != null
                && !clazz.getSuperclass().equals(Object.class)) {
            getMappedFiled(clazz.getSuperclass(), fields);
        }

        return fields;
    }
}

