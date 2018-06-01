package com.guming.shops.dto;

import com.guming.common.base.dto.BaseDto;
import com.guming.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/23 09:41
 */
@ApiModel(value = "ShopAddDto", description = "店铺添加实体")
public class ShopAddDto extends BaseDto {
    @ApiModelProperty(value = "店铺名称", required = true)
    private String name;
    @ApiModelProperty(value = "编码", required = true)
    private String code;
    @ApiModelProperty(value = "省", required = true)
    private String province;
    @ApiModelProperty(value = "市", required = true)
    private String city;
    @ApiModelProperty(value = "县", required = true)
    private String district;
    @ApiModelProperty(value = "详细地址", required = true)
    private String address;
    @ApiModelProperty(value = "经度", required = true)
    private Double lng;
    @ApiModelProperty(value = "纬度", required = true)
    private Double lat;
    @ApiModelProperty(value = "联系人", required = true)
    private String contact;
    @ApiModelProperty(value = "联系电话", required = true)
    private String phone;
    @ApiModelProperty(value = "店铺状态", required = true)
    private Integer status;

    private Date joinedTime;
    @ApiModelProperty(value = "加盟时间", required = true)
    private String joinedTimeStr;

    @ApiModelProperty(value = "加盟等级", required = true)
    private Long tagrankId;

    // 对应仓库ID
    @ApiModelProperty(value = "仓库ID组")
    private List<Long> tagwareHouseList;

    @ApiModelProperty(value = "用户信息组")
    private List<ShopUserDto> userDtos;

    public Date getJoinedTime() {
        if (!StringUtils.isEmpty(this.joinedTimeStr)) {
            this.joinedTime = DateUtil.parseDate(this.joinedTimeStr);
        }
        return this.joinedTime;
    }

    public void setJoinedTime(Date joinedTime) {
        this.joinedTime = joinedTime;
    }

    @Override
    public String toString() {
        return "ShopAddDto{" +
                "name='" + name + '\'' +
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
                ", joinedTime=" + joinedTime +
                ", joinedTimeStr='" + joinedTimeStr + '\'' +
                ", tagwareHouseList=" + tagwareHouseList +
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
        ShopAddDto that = (ShopAddDto) o;
        return Objects.equals(name, that.name) &&
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
                Objects.equals(joinedTime, that.joinedTime) &&
                Objects.equals(joinedTimeStr, that.joinedTimeStr) &&
                Objects.equals(tagwareHouseList, that.tagwareHouseList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, code, province, city, district, address, lng, lat, contact, phone, status, joinedTime, joinedTimeStr, tagwareHouseList);
    }

    public Integer getStatus() {

        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getJoinedTimeStr() {
        return joinedTimeStr;
    }

    public void setJoinedTimeStr(String joinedTimeStr) {
        this.joinedTimeStr = joinedTimeStr;
    }

    public List<Long> getTagwareHouseList() {
        return tagwareHouseList;
    }

    public void setTagwareHouseList(List<Long> tagwareHouseList) {
        this.tagwareHouseList = tagwareHouseList;
    }

    public Long getTagrankId() {
        return tagrankId;
    }

    public void setTagrankId(Long tagrankId) {
        this.tagrankId = tagrankId;
    }

    public List<ShopUserDto> getUserDtos() {
        return userDtos;
    }

    public void setUserDtos(List<ShopUserDto> userDtos) {
        this.userDtos = userDtos;
    }
}
