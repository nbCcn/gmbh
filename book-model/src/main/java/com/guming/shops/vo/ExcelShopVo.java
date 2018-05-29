package com.guming.shops.vo;


import com.guming.common.annotation.ExcelVOAttribute;
import com.guming.common.utils.DateUtil;

import java.util.Date;

/**
 * @Auther: Ccn
 * @Description: 店铺导入实体
 * @Date: 2018/5/10 10:45
 */
public class ExcelShopVo {

    @ExcelVOAttribute(name = "*店铺编号", column = "A")
    private String code;

    @ExcelVOAttribute(name = "*店铺名称", column = "B")
    private String name;

    private Date joinedTime;
    @ExcelVOAttribute(name = "*加盟时间", column = "C")
    private String joinedTimeStr;

    private Integer status;
    @ExcelVOAttribute(name = "*店铺状态", column = "D")
    private String statusStr;

    @ExcelVOAttribute(name = "*店铺等级", column = "E")
    private String tagrankStr;

    @ExcelVOAttribute(name = "*发货仓库", column = "F")
    private String tagwareHouse;

    @ExcelVOAttribute(name = "*省份", column = "G")
    private String province;

    @ExcelVOAttribute(name = "*城市", column = "H")
    private String city;

    @ExcelVOAttribute(name = "*区县", column = "I")
    private String district;

    @ExcelVOAttribute(name = "*详细地址", column = "J")
    private String address;

    @ExcelVOAttribute(name = "*经度", column = "K")
    private Double lng;

    @ExcelVOAttribute(name = "*纬度", column = "L")
    private Double lat;

    @ExcelVOAttribute(name = "*联系人", column = "M")
    private String contact;

    @ExcelVOAttribute(name = "*联系电话", column = "N")
    private String phone;


    @ExcelVOAttribute(name = "*店铺账号1", column = "O")
    private String username1;
    @ExcelVOAttribute(name = "*角色1", column = "P")
    private String identity1;

    @ExcelVOAttribute(name = "店铺账号2", column = "Q")
    private String username2;
    @ExcelVOAttribute(name = "角色2", column = "R")
    private String identity2;

    @ExcelVOAttribute(name = "店铺账号3", column = "S")
    private String username3;
    @ExcelVOAttribute(name = "角色3", column = "T")
    private String identity3;

    @ExcelVOAttribute(name = "店铺账号4", column = "U")
    private String username4;
    @ExcelVOAttribute(name = "角色4", column = "V")
    private String identity4;

    @ExcelVOAttribute(name = "店铺账号5", column = "W")
    private String username5;
    @ExcelVOAttribute(name = "角色5", column = "X")
    private String identity5;

    @ExcelVOAttribute(name = "终止标识列", column = "Y")
    private String end;

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
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

    public String getTagrankStr() {
        return tagrankStr;
    }

    public void setTagrankStr(String tagrankStr) {
        this.tagrankStr = tagrankStr;
    }

    public Date getJoinedTime() {
        if (this.joinedTimeStr != null) {
            this.joinedTime = DateUtil.parseDate(this.joinedTimeStr);
        }
        return joinedTime;
    }

    public void setJoinedTime(Date joinedTime) {
        this.joinedTime = joinedTime;
    }

    public String getTagwareHouse() {
        return tagwareHouse;
    }

    public void setTagwareHouse(String tagwareHouse) {
        this.tagwareHouse = tagwareHouse;
    }

    public String getJoinedTimeStr() {
        return joinedTimeStr;
    }

    public void setJoinedTimeStr(String joinedTimeStr) {
        this.joinedTimeStr = joinedTimeStr;
    }


    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getIdentity1() {
        return identity1;
    }

    public void setIdentity1(String identity1) {
        this.identity1 = identity1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getIdentity2() {
        return identity2;
    }

    public void setIdentity2(String identity2) {
        this.identity2 = identity2;
    }

    public String getUsername3() {
        return username3;
    }

    public void setUsername3(String username3) {
        this.username3 = username3;
    }

    public String getIdentity3() {
        return identity3;
    }

    public void setIdentity3(String identity3) {
        this.identity3 = identity3;
    }

    public String getUsername4() {
        return username4;
    }

    public void setUsername4(String username4) {
        this.username4 = username4;
    }

    public String getIdentity4() {
        return identity4;
    }

    public void setIdentity4(String identity4) {
        this.identity4 = identity4;
    }

    public String getUsername5() {
        return username5;
    }

    public void setUsername5(String username5) {
        this.username5 = username5;
    }

    public String getIdentity5() {
        return identity5;
    }

    public void setIdentity5(String identity5) {
        this.identity5 = identity5;
    }
}
