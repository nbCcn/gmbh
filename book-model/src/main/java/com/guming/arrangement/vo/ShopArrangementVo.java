package com.guming.arrangement.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/4 10:19
 */
@ApiModel(value = "ShopArrangementVo", description = "店铺送货安排实体")
public class ShopArrangementVo {

    @ApiModelProperty(value = "日期")
    private String dateStr;

    @ApiModelProperty(value = "星期")
    private String weekStr;

    @ApiModelProperty(value = "路线名称")
    private String tagLineName;

    @ApiModelProperty(value = "负责人")
    private String manager;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @Override
    public String toString() {
        return "ShopArrangementVo{" +
                "dateStr='" + dateStr + '\'' +
                ", weekStr='" + weekStr + '\'' +
                ", tagLineName='" + tagLineName + '\'' +
                ", manager='" + manager + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopArrangementVo that = (ShopArrangementVo) o;
        return Objects.equals(dateStr, that.dateStr) &&
                Objects.equals(weekStr, that.weekStr) &&
                Objects.equals(tagLineName, that.tagLineName) &&
                Objects.equals(manager, that.manager) &&
                Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateStr, weekStr, tagLineName, manager, phone);
    }

    public String getDateStr() {

        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getWeekStr() {
        return weekStr;
    }

    public void setWeekStr(String weekStr) {
        this.weekStr = weekStr;
    }

    public String getTagLineName() {
        return tagLineName;
    }

    public void setTagLineName(String tagLineName) {
        this.tagLineName = tagLineName;
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
}
