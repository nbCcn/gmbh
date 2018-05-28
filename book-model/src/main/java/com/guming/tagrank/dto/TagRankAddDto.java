package com.guming.tagrank.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/23 10:43
 */
@ApiModel(value = "TagRankAddDto", description = "等级添加实体")
public class TagRankAddDto extends BaseDto {

    @ApiModelProperty(value = "店铺名称", required = true)
    private String name;
    @ApiModelProperty(value = "是否筹备", required = true)
    private Integer isPrepare;
    @ApiModelProperty(value = "排序码")
    private Integer orderCode;

    @Override
    public String toString() {
        return "TagRankAddDto{" +
                "name='" + name + '\'' +
                ", isPrepare=" + isPrepare +
                ", orderCode=" + orderCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagRankAddDto that = (TagRankAddDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(isPrepare, that.isPrepare) &&
                Objects.equals(orderCode, that.orderCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, isPrepare, orderCode);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsPrepare() {
        return isPrepare;
    }

    public void setIsPrepare(Integer isPrepare) {
        this.isPrepare = isPrepare;
    }

    public Integer getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Integer orderCode) {
        this.orderCode = orderCode;
    }
}
