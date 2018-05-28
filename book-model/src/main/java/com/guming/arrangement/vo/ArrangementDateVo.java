package com.guming.arrangement.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/2 18:36
 */
@ApiModel(value = "ArrangementDateVo", description = "路线安排日期实体")
public class ArrangementDateVo {

    @ApiModelProperty(value = "路线安排年份Str")
    private String yearStr;

    @ApiModelProperty(value = "路线安排月份Str")
    private String monthStr;

    @ApiModelProperty(value = "路线日安排")
    private List<ArrangementDayVo> arrangementDayVoList;


    @Override
    public String toString() {
        return "ArrangementDateVo{" +
                "yearStr='" + yearStr + '\'' +
                ", monthStr='" + monthStr + '\'' +
                ", arrangementDayVoList=" + arrangementDayVoList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrangementDateVo that = (ArrangementDateVo) o;
        return Objects.equals(yearStr, that.yearStr) &&
                Objects.equals(monthStr, that.monthStr) &&
                Objects.equals(arrangementDayVoList, that.arrangementDayVoList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(yearStr, monthStr, arrangementDayVoList);
    }

    public String getYearStr() {
        return yearStr;
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr;
    }

    public String getMonthStr() {
        return monthStr;
    }

    public void setMonthStr(String monthStr) {
        this.monthStr = monthStr;
    }

    public List<ArrangementDayVo> getArrangementDayVoList() {
        return arrangementDayVoList;
    }

    public void setArrangementDayVoList(List<ArrangementDayVo> arrangementDayVoList) {
        this.arrangementDayVoList = arrangementDayVoList;
    }
}
