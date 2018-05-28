package com.guming.arrangement.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/2 10:58
 */
@ApiModel(value = "ArrangementReMoveDto", description = "路线安排移除实体")
public class ArrangementReMoveDto {

    @ApiModelProperty(value = "路线规划ID")
    private Long pathId;
    @ApiModelProperty(value = "路线安排ID")
    private Long arrangementId;

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public Long getArrangementId() {
        return arrangementId;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrangementReMoveDto that = (ArrangementReMoveDto) o;
        return Objects.equals(pathId, that.pathId) &&
                Objects.equals(arrangementId, that.arrangementId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pathId, arrangementId);
    }

    @Override
    public String toString() {
        return "ArrangementReMoveDto{" +
                "pathId=" + pathId +
                ", arrangementId=" + arrangementId +
                '}';
    }
}
