package com.guming.products.vo;

import com.guming.common.annotation.ExcelVOAttribute;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/28 15:06
 */
@ApiModel(value = "ProductsImportVo", description = "商品信息导入实体")
@Data
public class ProductsImportVo {

    @ExcelVOAttribute(name = "*商品编号", column = "A")
    private String code;
    @ExcelVOAttribute(name = "*商品名称", column = "B")
    private String name;
    @ExcelVOAttribute(name = "*商品分类", column = "C")
    private String productsCategoryName;
    @ExcelVOAttribute(name = "*发货单位", column = "D")
    private String sendUnit;
    @ExcelVOAttribute(name = "*最小购买单位", column = "E")
    private String minUnit;
    @ExcelVOAttribute(name = "*换算率", column = "F")
    private Integer stock;
    @ExcelVOAttribute(name = "*商品规格", column = "G")
    private String spec;
    @ExcelVOAttribute(name = "*商品单格", column = "H")
    private Double price;
    @ExcelVOAttribute(name = "最多可订", column = "I")
    private Integer mixLimit;
    @ExcelVOAttribute(name = "*步长", column = "G")
    private Integer step;
    @ExcelVOAttribute(name = "*店铺等级", column = "K")
    private String tagRank;
    @ExcelVOAttribute(name = "*店铺状态", column = "L")
    private String status;
    @ExcelVOAttribute(name = "*对应仓库", column = "M")
    private String tagwareHouse;
    @ExcelVOAttribute(name = "指定路线", column = "N")
    private String tagLine;
    @ExcelVOAttribute(name = "*是否上架", column = "O")
    private String isUp;
    @ExcelVOAttribute(name = "商品描述", column = "P")
    private String describe;
    @ExcelVOAttribute(name = "排序码", column = "Q")
    private Long order;
}
