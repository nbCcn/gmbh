package com.guming.plans.dto.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/28 10:01
 */
@ApiModel(value = "ShopFuzzyQuery",description = "店铺模糊查询实体")
public class ShopFuzzyQuery {


    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @Override
    public String toString() {
        return "ShopFuzzyQuery{" +
                "shopName='" + shopName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopFuzzyQuery that = (ShopFuzzyQuery) o;
        return Objects.equals(shopName, that.shopName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(shopName);
    }

    public String getShopName() {

        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
