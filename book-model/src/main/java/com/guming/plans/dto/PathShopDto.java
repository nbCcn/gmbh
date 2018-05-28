package com.guming.plans.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/27 16:29
 */
@ApiModel(value = "PathShopDto", description = "路线规划新增实体")
public class PathShopDto {

    @ApiModelProperty(value = "移动之前路线规划的Id")
    private Long oldPathId;

    @ApiModelProperty(value = "被移动店铺的ID")
    private Long shopId;

    @Override
    public String toString() {
        return "PathShopDto{" +
                "oldPathId=" + oldPathId +
                ", shopId=" + shopId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathShopDto that = (PathShopDto) o;
        return Objects.equals(oldPathId, that.oldPathId) &&
                Objects.equals(shopId, that.shopId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(oldPathId, shopId);
    }

    public Long getOldPathId() {

        return oldPathId;
    }

    public void setOldPathId(Long oldPathId) {
        this.oldPathId = oldPathId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
