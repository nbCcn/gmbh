package com.guming.arrangement.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/2 10:58
 */
@ApiModel(value = "ArrangementMoveDto", description = "路线安排新增实体")
public class ArrangementMoveDto {

    @ApiModelProperty(value = "路线规划ID")
    private Long pathId;
    @ApiModelProperty(value = "路线安排日期Str")
    private String dayStr;

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public String getDayStr() {
        return dayStr;
    }

    public void setDayStr(String dayStr) {
        this.dayStr = dayStr;
    }
}
