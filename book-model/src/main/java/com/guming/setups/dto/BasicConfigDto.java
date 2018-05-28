package com.guming.setups.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/1
 */
@ApiModel(value = "BasicConfigDto",description = "返回的基础配置实体")
public class BasicConfigDto {

    @ApiModelProperty(value = "是否允许筹备阶段报货")
    private Boolean canPrepareOrdered;

    @ApiModelProperty(value = "是否有特殊订单报货")
    private Boolean canSpecialOrdered;

    @ApiModelProperty(value = "特殊订单报货周期（1：周，2：月）")
    private Integer orderCycle;

    @ApiModelProperty(value = "特殊订单报货日期")
    private Integer orderDay;

    @ApiModelProperty(value = "是否许需要补单")
    private Boolean canSupplement;

    @ApiModelProperty(value = "是否开启审核")
    private Boolean canChecked;

    @ApiModelProperty(value = "提前开始天数")
    private Integer advanceStartDay;

    @ApiModelProperty(value = "提前结束天数")
    private Integer advanceEndDay;

    @ApiModelProperty(value = "第三方链接")
    private String thirdUrl;

    public Boolean getCanPrepareOrdered() {
        return canPrepareOrdered;
    }

    public void setCanPrepareOrdered(Boolean canPrepareOrdered) {
        this.canPrepareOrdered = canPrepareOrdered;
    }

    public Boolean getCanSpecialOrdered() {
        return canSpecialOrdered;
    }

    public void setCanSpecialOrdered(Boolean canSpecialOrdered) {
        this.canSpecialOrdered = canSpecialOrdered;
    }

    public Integer getOrderCycle() {
        return orderCycle;
    }

    public void setOrderCycle(Integer orderCycle) {
        this.orderCycle = orderCycle;
    }

    public Integer getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(Integer orderDay) {
        this.orderDay = orderDay;
    }

    public Boolean getCanSupplement() {
        return canSupplement;
    }

    public void setCanSupplement(Boolean canSupplement) {
        this.canSupplement = canSupplement;
    }

    public Boolean getCanChecked() {
        return canChecked;
    }

    public void setCanChecked(Boolean canChecked) {
        this.canChecked = canChecked;
    }

    public Integer getAdvanceStartDay() {
        return advanceStartDay;
    }

    public void setAdvanceStartDay(Integer advanceStartDay) {
        this.advanceStartDay = advanceStartDay;
    }

    public Integer getAdvanceEndDay() {
        return advanceEndDay;
    }

    public void setAdvanceEndDay(Integer advanceEndDay) {
        this.advanceEndDay = advanceEndDay;
    }

    public String getThirdUrl() {
        return thirdUrl;
    }

    public void setThirdUrl(String thirdUrl) {
        this.thirdUrl = thirdUrl;
    }
}
