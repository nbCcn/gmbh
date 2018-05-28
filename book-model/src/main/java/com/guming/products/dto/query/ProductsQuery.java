package com.guming.products.dto.query;

import com.guming.common.base.dto.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/18
 */
@ApiModel(value = "ProductsQuery",description = "商品分页查询实体")
public class ProductsQuery extends PageQuery {

    @ApiModelProperty(value = "商品名")
    private String name;

    @ApiModelProperty(value = "商品类型id")
    private Long classifyId;

    @ApiModelProperty(value = "是否上架")
    private Boolean isUp;

    @ApiModelProperty(value = "仓库id组")
    private List<Long> warehouseIds;

    @ApiModelProperty(value = "路线id组")
    private List<Long> tagLineIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Boolean getIsUp() {
        return isUp;
    }

    public void setIsUp(Boolean isUp) {
        this.isUp = isUp;
    }

    public List<Long> getWarehouseIds() {
        return warehouseIds;
    }

    public void setWarehouseIds(List<Long> warehouseIds) {
        this.warehouseIds = warehouseIds;
    }

    public List<Long> getTagLineIds() {
        return tagLineIds;
    }

    public void setTagLineIds(List<Long> tagLineIds) {
        this.tagLineIds = tagLineIds;
    }
}
