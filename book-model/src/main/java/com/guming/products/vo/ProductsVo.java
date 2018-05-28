package com.guming.products.vo;

import com.guming.common.base.vo.MapVo;
import com.guming.orderTemplate.vo.TemplateProductsVo;
import com.guming.products.entity.ProductsHistory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/18
 */
@ApiModel(value = "ProductsVo",description = "返回的商品信息实体")
@Data
public class ProductsVo {
    @ApiModelProperty(value = "商品id")
    private Long id;

    @ApiModelProperty(value = "商品code")
    private String code;

    @ApiModelProperty(value = "商品名")
    private String name;

    @ApiModelProperty(value = "规格")
    private String spec;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "换算率")
    private Integer stock;

    @ApiModelProperty(value = "换算单位")
    private String stockUnit;

    @ApiModelProperty(value = "限购数")
    private Integer limit;

    @ApiModelProperty(value = "步进")
    private Integer step;

    @ApiModelProperty(value = "排序码")
    private Long order;

    @ApiModelProperty(value = "是否上架")
    private Boolean isUp;

    @ApiModelProperty(value = "是否过滤路线")
    private Boolean needTagLines;

    @ApiModelProperty(value = "描述")
    private String describe;

    @ApiModelProperty(value = "商品分类信息")
    private MapVo productsClassifyMapVo;

    @ApiModelProperty(value = "仓库信息组")
    private List<MapVo> warehouseMapVoList;

    @ApiModelProperty(value = "支持的店铺状态")
    private List<MapVo> shopStatusMapVoList;

    @ApiModelProperty(value = "支持的店铺等级")
    private List<MapVo> setupsTagrankMapVoList;

    @ApiModelProperty(value = "过滤的路线信息组")
    private List<MapVo> taglineMapVoList;

    @ApiModelProperty(value = "历史价格数据")
    private  List<ProductsHistory> productsHistoryList;

    @ApiModelProperty(value = "商品所在模板数据")
    private List<TemplateProductsVo> templateProductsVoList;
}
