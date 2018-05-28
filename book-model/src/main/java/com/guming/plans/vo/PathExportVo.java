package com.guming.plans.vo;



import com.guming.common.annotation.ExcelVOAttribute;

import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:店铺返回参数
 * @Date: 2018/4/12 15:02
 */
public class PathExportVo {

    @ExcelVOAttribute(name = "仓库名称", column = "A")
    private String tagwareHouseName;
    @ExcelVOAttribute(name = "路线名称", column = "B")
    private String tagLineName;
    @ExcelVOAttribute(name = "店铺名称", column = "C")
    private String name;
    @ExcelVOAttribute(name = "店铺编码", column = "D")
    private String code;
    @ExcelVOAttribute(name = "店铺地址", column = "E")
    private String address;
    @ExcelVOAttribute(name = "联系人", column = "F")
    private String contact;
    @ExcelVOAttribute(name = "联系电话", column = "G")
    private String phone;

    @ExcelVOAttribute(name = "店铺状态", column = "H")
    private String statusStr;


    public PathExportVo(String tagwareHouseName, String tagLineName, String name, String code, String address, String contact, String phone, Integer status) {
        this.tagwareHouseName = tagwareHouseName;
        this.tagLineName = tagLineName;
        this.name = name;
        this.code = code;
        this.address = address;
        this.contact = contact;
        this.phone = phone;
    }

    public PathExportVo() {
    }

    @Override
    public String toString() {
        return "PathExportVo{" +
                "tagwareHouseName='" + tagwareHouseName + '\'' +
                ", tagLineName='" + tagLineName + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", statusStr='" + statusStr + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathExportVo that = (PathExportVo) o;
        return Objects.equals(tagwareHouseName, that.tagwareHouseName) &&
                Objects.equals(tagLineName, that.tagLineName) &&
                Objects.equals(name, that.name) &&
                Objects.equals(code, that.code) &&
                Objects.equals(address, that.address) &&
                Objects.equals(contact, that.contact) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(statusStr, that.statusStr);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tagwareHouseName, tagLineName, name, code, address, contact, phone,  statusStr);
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
}
