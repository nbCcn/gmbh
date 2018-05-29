package com.guming.plans.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/24 16:56
 */
@ApiModel(value = "PathVo", description = "返回的路线规划实体")
public class PathVo {

    @ApiModelProperty(value = "仓库Id")
    private Long tagwareHouseId;

    @ApiModelProperty(value = "路线规划Id")
    private Long pathId;

    @ApiModelProperty(value = "路线id")
    private Long tagLineId;

    @ApiModelProperty(value = "路线名称")
    private String tagLineName;

    @ApiModelProperty(value = "店铺数量")
    private Integer count;

    public PathVo(Long tagwareHouseId, Long pathId, Long tagLineId, String tagLineName) {
        this.tagwareHouseId = tagwareHouseId;
        this.pathId = pathId;
        this.tagLineId = tagLineId;
        this.tagLineName = tagLineName;
    }

    public PathVo() {
    }

    @Override
    public String toString() {
        return "PathVo{" +
                "tagwareHouseId=" + tagwareHouseId +
                ", pathId=" + pathId +
                ", tagLineId=" + tagLineId +
                ", tagLineName='" + tagLineName + '\'' +
                ", count=" + count +
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
        PathVo pathVo = (PathVo) o;
        return Objects.equals(tagwareHouseId, pathVo.tagwareHouseId) &&
                Objects.equals(pathId, pathVo.pathId) &&
                Objects.equals(tagLineId, pathVo.tagLineId) &&
                Objects.equals(tagLineName, pathVo.tagLineName) &&
                Objects.equals(count, pathVo.count);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tagwareHouseId, pathId, tagLineId, tagLineName, count);
    }

    public Long getTagwareHouseId() {

        return tagwareHouseId;
    }

    public void setTagwareHouseId(Long tagwareHouseId) {
        this.tagwareHouseId = tagwareHouseId;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public Long getTagLineId() {
        return tagLineId;
    }

    public void setTagLineId(Long tagLineId) {
        this.tagLineId = tagLineId;
    }

    public String getTagLineName() {
        return tagLineName;
    }

    public void setTagLineName(String tagLineName) {
        this.tagLineName = tagLineName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
