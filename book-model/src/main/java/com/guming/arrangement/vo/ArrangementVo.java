package com.guming.arrangement.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/3 11:00
 */
@ApiModel(value = "ArrangementVo", description = "路线安排实体")
public class ArrangementVo {

    @ApiModelProperty(value = "路线规划Id")
    private Long pathId;
    @ApiModelProperty(value = "路线安排Id")
    private Long arrangementId;
    @ApiModelProperty(value = "路线名称")
    private String tagLineName;

    @Override
    public String toString() {
        return "ArrangementVo{" +
                "pathId=" + pathId +
                ", arrangementId=" + arrangementId +
                ", tagLineName='" + tagLineName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrangementVo that = (ArrangementVo) o;
        return Objects.equals(pathId, that.pathId) &&
                Objects.equals(arrangementId, that.arrangementId) &&
                Objects.equals(tagLineName, that.tagLineName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pathId, arrangementId, tagLineName);
    }

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

    public String getTagLineName() {
        return tagLineName;
    }

    public void setTagLineName(String tagLineName) {
        this.tagLineName = tagLineName;
    }
}
