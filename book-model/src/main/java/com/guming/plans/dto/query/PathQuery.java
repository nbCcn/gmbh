package com.guming.plans.dto.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/24 11:49
 */
@ApiModel(value = "PathQuery", description = "路线规划查询实体")
public class PathQuery {

    @ApiModelProperty(value = "路线规划Id")
    private Long pathId;

    @ApiModelProperty(value = "仓库Id")
    private Long tagwareHouseId;

    @Override
    public String toString() {
        return "PathQuery{" +
                "pathId=" + pathId +
                ", tagwareHouseId=" + tagwareHouseId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathQuery pathQuery = (PathQuery) o;
        return Objects.equals(pathId, pathQuery.pathId) &&
                Objects.equals(tagwareHouseId, pathQuery.tagwareHouseId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pathId, tagwareHouseId);
    }

    public Long getPathId() {

        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public Long getTagwareHouseId() {
        return tagwareHouseId;
    }

    public void setTagwareHouseId(Long tagwareHouseId) {
        this.tagwareHouseId = tagwareHouseId;
    }

}
