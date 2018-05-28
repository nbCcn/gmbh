package com.guming.arrangement.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/2 18:30
 */
@ApiModel(value = "ArrangementDayVo", description = "每日路线安排实体")
public class ArrangementDayVo {

    @ApiModelProperty(value = "日")
    private String day;

    @ApiModelProperty(value = "数据Vo")
    private List<ArrangementVo> arrangementVoList = null;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<ArrangementVo> getArrangementVoList() {
        return arrangementVoList;
    }

    public void setArrangementVoList(List<ArrangementVo> arrangementVoList) {
        this.arrangementVoList = arrangementVoList;
    }
}
