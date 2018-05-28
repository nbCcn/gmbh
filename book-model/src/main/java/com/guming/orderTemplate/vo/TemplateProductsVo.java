package com.guming.orderTemplate.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/20
 */
public class TemplateProductsVo {

    @Setter
    @ApiModelProperty(value = "数量")
    private Integer amount;

    @Setter
    @Getter
    @ApiModelProperty(value = "商品模板关联id")
    private Long id;

    @Setter
    @Getter
    @ApiModelProperty(value = "商品code")
    private String code;

    @Setter
    @Getter
    @ApiModelProperty(value = "商品名")
    private String productName;

    @Setter
    @Getter
    @ApiModelProperty(value = "规格")
    private String spec;

    @Setter
    @Getter
    @ApiModelProperty(value = "单位")
    private String unit;

    @Setter
    @Getter
    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @Setter
    @Getter
    @ApiModelProperty(value = "換算率")
    private Integer stock;

    @Setter
    @Getter
    @ApiModelProperty(value = "換算單位")
    private String stockUnit;

    @Setter
    @Getter
    @ApiModelProperty(value = "步長")
    private Integer step;

    @Setter
    @Getter
    @ApiModelProperty(value = "限购量")
    private Integer limit;

    @Setter
    @Getter
    @ApiModelProperty(value = "分类")
    private String classifyName;

    @Setter
    @Getter
    @ApiModelProperty(value = "模板名")
    private String templateName;

    @Setter
    @Getter
    @ApiModelProperty(value = "模板id")
    private Long templateId;

    public Integer getAmount() {
        if (this.amount == null){
            return 0;
        }
        return amount;
    }
}
