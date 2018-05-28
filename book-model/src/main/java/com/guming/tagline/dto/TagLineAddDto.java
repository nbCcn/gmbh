package com.guming.tagline.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/23 10:20
 */
@ApiModel(value = "TagLineAddDto", description = "送货路线添加实体")
public class TagLineAddDto extends BaseDto {

    @ApiModelProperty(value = "路线名称", required = true)
    private String name;
    @ApiModelProperty(value = "物流类型", required = true)
    private Integer ftype;
    @ApiModelProperty(value = "负责人")
    private String manager;
    @ApiModelProperty(value = "联系方式")
    private String phone;
    @ApiModelProperty(value = "排序码")
    private Integer orderCode;
    @ApiModelProperty(value = "仓库Id")
    private Long tagwareHouseId;


    @Override
    public String toString() {
        return "TagLineAddDto{" +
                "name='" + name + '\'' +
                ", ftype=" + ftype +
                ", manager='" + manager + '\'' +
                ", phone='" + phone + '\'' +
                ", orderCode=" + orderCode +
                ", tagwareHouseId=" + tagwareHouseId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagLineAddDto that = (TagLineAddDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(ftype, that.ftype) &&
                Objects.equals(manager, that.manager) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(orderCode, that.orderCode) &&
                Objects.equals(tagwareHouseId, that.tagwareHouseId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, ftype, manager, phone, orderCode, tagwareHouseId);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Integer orderCode) {
        this.orderCode = orderCode;
    }

    public Long getTagwareHouseId() {
        return tagwareHouseId;
    }

    public void setTagwareHouseId(Long tagwareHouseId) {
        this.tagwareHouseId = tagwareHouseId;
    }
}
