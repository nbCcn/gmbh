package com.guming.shops.vo;


import com.guming.tagwareHouse.vo.TagwareHouseVo;
import com.guming.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Ccn
 * @Description:店铺返回参数
 * @Date: 2018/4/12 15:02
 */
@ApiModel(value = "ShopVo", description = "返回的店铺信息实体")
public class ShopVo {
    @ApiModelProperty(value = "店铺Id")
    private Long id;
    @ApiModelProperty(value = "店铺名称")
    private String name;
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

    @ApiModelProperty(value = "仓库信息组")
    private List<TagwareHouseVo> tagwareHouseVoList;

    @ApiModelProperty(value = "加盟等级ID", required = true)
    private Long tagrankId;

    @ApiModelProperty(value = "加盟等级", required = true)
    private String tagrankName;

    @ApiModelProperty(value = "用户信息组")
    private List<ShopUserVo> userVoList;

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public List<TagwareHouseVo> getTagwareHouseVoList() {
        return tagwareHouseVoList;
    }

    public void setTagwareHouseVoList(List<TagwareHouseVo> tagwareHouseVoList) {
        this.tagwareHouseVoList = tagwareHouseVoList;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTagrankName() {
        return tagrankName;
    }

    public void setTagrankName(String tagrankName) {
        this.tagrankName = tagrankName;
    }

    public List<ShopUserVo> getUserVoList() {
        return userVoList;
    }

    public void setUserVoList(List<ShopUserVo> userVoList) {
        this.userVoList = userVoList;
    }

    public Long getTagrankId() {
        return tagrankId;
    }

    public void setTagrankId(Long tagrankId) {
        this.tagrankId = tagrankId;
    }
}
