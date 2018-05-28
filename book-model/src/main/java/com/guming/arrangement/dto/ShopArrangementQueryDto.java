package com.guming.arrangement.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/4 10:24
 */
@ApiModel(value = "ShopArrangementQueryDto", description = "根据店铺查询路线安排实体")
public class ShopArrangementQueryDto {

    @ApiModelProperty(value = "路线安排日期Str")
    private String dayStr;

    @ApiModelProperty(value = "店铺Id")
    private Long shopId;

    public String getDayStr() {
        return dayStr;
    }

    public void setDayStr(String dayStr) {
        this.dayStr = dayStr;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
