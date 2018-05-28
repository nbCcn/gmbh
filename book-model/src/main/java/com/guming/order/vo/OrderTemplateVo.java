package com.guming.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/29
 */
@Data
@ApiModel(value = "OrderTemplateVo",description = "订单关联商品信息实体")
public class OrderTemplateVo {
    @ApiModelProperty(value = "关联数据段id")
    private Long id;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "商品名")
    private String productName;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "数量")
    private Integer amount;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "规格")
    private String spec;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "换算率")
    private Integer stock;

    @ApiModelProperty(value = "换算单位")
    private String stockUnit;

    @ApiModelProperty(value = "步長")
    private Integer step;

    @ApiModelProperty(value = "是否还保留在订单中")
    private Boolean isValid;
}
