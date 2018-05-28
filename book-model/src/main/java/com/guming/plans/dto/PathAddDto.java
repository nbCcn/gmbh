package com.guming.plans.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/24 11:50
 */
@ApiModel(value = "PathAddDto", description = "路线规划移动实体")
public class PathAddDto extends BaseDto {

    @ApiModelProperty(value = "移动后的路线规划Id")
    private Long newPathId;

    @ApiModelProperty(value = "店铺与路线组合对象")
    private List<PathShopDto> pathShopDtos;

    @Override
    public String toString() {
        return "PathAddDto{" +
                "newPathId=" + newPathId +
                ", pathShopDtos=" + pathShopDtos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathAddDto that = (PathAddDto) o;
        return Objects.equals(newPathId, that.newPathId) &&
                Objects.equals(pathShopDtos, that.pathShopDtos);
    }

    @Override
    public int hashCode() {

        return Objects.hash(newPathId, pathShopDtos);
    }

    public Long getNewPathId() {

        return newPathId;
    }

    public void setNewPathId(Long newPathId) {
        this.newPathId = newPathId;
    }

    public List<PathShopDto> getPathShopDtos() {
        return pathShopDtos;
    }

    public void setPathShopDtos(List<PathShopDto> pathShopDtos) {
        this.pathShopDtos = pathShopDtos;
    }
}
