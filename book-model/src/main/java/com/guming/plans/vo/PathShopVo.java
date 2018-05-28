package com.guming.plans.vo;


import com.guming.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:店铺返回参数
 * @Date: 2018/4/12 15:02
 */
@ApiModel(value = "PathShopVo", description = "根据路线返回的店铺信息实体")
public class PathShopVo {

    @ApiModelProperty(value = "仓库名称")
    private String tagwareHouseName;

    @ApiModelProperty(value = "仓库Id")
    private Long tagwareHouseId;

    @ApiModelProperty(value = "路线规划Id")
    private Long pathId;

    @ApiModelProperty(value = "路线名称")
    private String tagLineName;

    @ApiModelProperty(value = "店铺Id")
    private Long shopId;
    @ApiModelProperty(value = "店铺名称")
    private String name;
    @ApiModelProperty(value = "店铺别名")
    private String ename;
    @ApiModelProperty(value = "编码")
    private String code;
    @ApiModelProperty(value = "省")
    private String province;
    @ApiModelProperty(value = "市")
    private String city;
    @ApiModelProperty(value = "县")
    private String district;
    @ApiModelProperty(value = "详细地址")
    private String address;
    @ApiModelProperty(value = "经度")
    private Double lng;
    @ApiModelProperty(value = "纬度")
    private Double lat;
    @ApiModelProperty(value = "联系人")
    private String contact;
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "店铺状态")
    private Integer status;
    @ApiModelProperty(value = "店铺状态Str")
    private String statusStr;

    private Date joinedTime;
    @ApiModelProperty(value = "加盟时间")
    private String joinedTimeStr;


    private Date createdTime;
    @ApiModelProperty(value = "商品id")
    private String createdTimeStr;
    @ApiModelProperty(value = "创建时间")
    private Date updatedTime;


    @ApiModelProperty(value = "修改时间")
    private String updatedTimeStr;


    @Override
    public String toString() {
        return "PathShopVo{" +
                "tagwareHouseName='" + tagwareHouseName + '\'' +
                ", tagwareHouseId=" + tagwareHouseId +
                ", pathId=" + pathId +
                ", tagLineName='" + tagLineName + '\'' +
                ", shopId=" + shopId +
                ", name='" + name + '\'' +
                ", ename='" + ename + '\'' +
                ", code='" + code + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", statusStr='" + statusStr + '\'' +
                ", joinedTime=" + joinedTime +
                ", joinedTimeStr='" + joinedTimeStr + '\'' +
                ", createdTime=" + createdTime +
                ", createdTimeStr='" + createdTimeStr + '\'' +
                ", updatedTime=" + updatedTime +
                ", updatedTimeStr='" + updatedTimeStr + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PathShopVo that = (PathShopVo) o;
        return Objects.equals(tagwareHouseName, that.tagwareHouseName) &&
                Objects.equals(tagwareHouseId, that.tagwareHouseId) &&
                Objects.equals(pathId, that.pathId) &&
                Objects.equals(tagLineName, that.tagLineName) &&
                Objects.equals(shopId, that.shopId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(ename, that.ename) &&
                Objects.equals(code, that.code) &&
                Objects.equals(province, that.province) &&
                Objects.equals(city, that.city) &&
                Objects.equals(district, that.district) &&
                Objects.equals(address, that.address) &&
                Objects.equals(lng, that.lng) &&
                Objects.equals(lat, that.lat) &&
                Objects.equals(contact, that.contact) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(status, that.status) &&
                Objects.equals(statusStr, that.statusStr) &&
                Objects.equals(joinedTime, that.joinedTime) &&
                Objects.equals(joinedTimeStr, that.joinedTimeStr) &&
                Objects.equals(createdTime, that.createdTime) &&
                Objects.equals(createdTimeStr, that.createdTimeStr) &&
                Objects.equals(updatedTime, that.updatedTime) &&
                Objects.equals(updatedTimeStr, that.updatedTimeStr);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tagwareHouseName, tagwareHouseId, pathId, tagLineName, shopId, name, ename, code, province, city, district, address, lng, lat, contact, phone, status, statusStr, joinedTime, joinedTimeStr, createdTime, createdTimeStr, updatedTime, updatedTimeStr);
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Long getTagwareHouseId() {

        return tagwareHouseId;
    }

    public void setTagwareHouseId(Long tagwareHouseId) {
        this.tagwareHouseId = tagwareHouseId;
    }

    public Long getShopId() {

        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public String getTagwareHouseName() {
        return tagwareHouseName;
    }

    public void setTagwareHouseName(String tagwareHouseName) {
        this.tagwareHouseName = tagwareHouseName;
    }

    public String getTagLineName() {
        return tagLineName;
    }

    public void setTagLineName(String tagLineName) {
        this.tagLineName = tagLineName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getJoinedTime() {
        return joinedTime;
    }

    public void setJoinedTime(Date joinedTime) {
        this.joinedTime = joinedTime;
    }

    public String getJoinedTimeStr() {
        if (this.joinedTime != null) {
            this.joinedTimeStr = DateUtil.formatDatetime(this.joinedTime);
        }
        return joinedTimeStr;
    }

    public void setJoinedTimeStr(String joinedTimeStr) {
        this.joinedTimeStr = joinedTimeStr;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedTimeStr() {
        if (this.createdTime != null) {
            this.createdTimeStr = DateUtil.formatDatetime(this.createdTime);
        }
        return createdTimeStr;
    }

    public void setCreatedTimeStr(String createdTimeStr) {
        this.createdTimeStr = createdTimeStr;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedTimeStr() {
        if (this.updatedTime != null) {
            this.updatedTimeStr = DateUtil.formatDatetime(this.updatedTime);
        }
        return updatedTimeStr;
    }

    public void setUpdatedTimeStr(String updatedTimeStr) {
        this.updatedTimeStr = updatedTimeStr;
    }
}
