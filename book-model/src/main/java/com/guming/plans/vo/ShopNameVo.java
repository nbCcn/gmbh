package com.guming.plans.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/28 10:40
 */
@ApiModel(value = "ShopNameVo", description = "返回的店铺名称实体")
public class ShopNameVo {

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @Override
    public String toString() {
        return "ShopNameVo{" +
                "shopName='" + shopName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopNameVo that = (ShopNameVo) o;
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
