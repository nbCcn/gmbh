package com.guming.products.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/23
 */
@ApiModel(value = "ProductsAddDto",description = "商品添加用实体")
public class ProductsAddDto extends BaseDto {

    @ApiModelProperty(value = "商品code",required = true)
    private String code;

    @ApiModelProperty(value = "商品名",required = true)
    private String name;

    @ApiModelProperty(value = "规格",required = true)
    private String spec;

    @ApiModelProperty(value = "单位",required = true)
    private String unit;

    @ApiModelProperty(value = "价格",required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "换算率",required = true)
    private Integer stock;

    @ApiModelProperty(value = "换算单位",required = true)
    private String stockUnit;

    @ApiModelProperty(value = "限购数",required = true)
    private Integer limit;

    @ApiModelProperty(value = "步进",required = true)
    private Integer step;

    @ApiModelProperty(value = "排序码")
    private Long order;

    @ApiModelProperty(value = "是否上架(默认true)",required = true)
    private Boolean isUp=true;

    @ApiModelProperty(value = "是否过滤路线(默认false)",required = true)
    private Boolean needTagLines=false;

    @ApiModelProperty(value = "描述")
    private String describe;

    @ApiModelProperty(value = "商品分类id",required = true)
    private Long productsClassifyId;

    @ApiModelProperty(value = "仓库id组")
    private List<Long> warehouseIdList;

    @ApiModelProperty(value = "店铺状态id组")
    private List<Long> shopStatusIdList;

    @ApiModelProperty(value = "支持的店铺等级id组")
    private List<Long> tagrankIdList;

    @ApiModelProperty(value = "过滤的路线id组")
    private List<Long> taglineIdList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Boolean getIsUp() {
        return isUp;
    }

    public void setIsUp(Boolean isUp) {
        this.isUp = isUp;
    }

    public Boolean getNeedTagLines() {
        return needTagLines;
    }

    public void setNeedTagLines(Boolean needTagLines) {
        this.needTagLines = needTagLines;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Long getProductsClassifyId() {
        return productsClassifyId;
    }

    public void setProductsClassifyId(Long productsClassifyId) {
        this.productsClassifyId = productsClassifyId;
    }

    public List<Long> getWarehouseIdList() {
        return warehouseIdList;
    }

    public void setWarehouseIdList(List<Long> warehouseIdList) {
        this.warehouseIdList = warehouseIdList;
    }

    public List<Long> getTagrankIdList() {
        return tagrankIdList;
    }

    public void setTagrankIdList(List<Long> tagrankIdList) {
        this.tagrankIdList = tagrankIdList;
    }

    public List<Long> getTaglineIdList() {
        return taglineIdList;
    }

    public void setTaglineIdList(List<Long> taglineIdList) {
        this.taglineIdList = taglineIdList;
    }

    public String getStockUnit() {
        return stockUnit;
    }

    public void setStockUnit(String stockUnit) {
        this.stockUnit = stockUnit;
    }

    public List<Long> getShopStatusIdList() {
        return shopStatusIdList;
    }

    public void setShopStatusIdList(List<Long> shopStatusIdList) {
        this.shopStatusIdList = shopStatusIdList;
    }
}
