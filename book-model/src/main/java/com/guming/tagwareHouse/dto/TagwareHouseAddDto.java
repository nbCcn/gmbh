package com.guming.tagwareHouse.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/23 11:12
 */
@ApiModel(value = "TagwareHouseAddDto", description = "仓库新增实体")
public class TagwareHouseAddDto extends BaseDto {

    @ApiModelProperty(value = "仓库名称", required = true)
    private String name;
    @ApiModelProperty(value = "分组")
    private Integer groupId;
    @ApiModelProperty(value = "经度")
    private Double lng;
    @ApiModelProperty(value = "纬度")
    private Double lat;
    @ApiModelProperty(value = "webhook")
    private String webhook;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagwareHouseAddDto that = (TagwareHouseAddDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(groupId, that.groupId) &&
                Objects.equals(lng, that.lng) &&
                Objects.equals(lat, that.lat) &&
                Objects.equals(webhook, that.webhook);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, groupId, lng, lat, webhook);
    }

    @Override
    public String toString() {
        return "TagwareHouseAddDto{" +
                "name='" + name + '\'' +
                ", groupId=" + groupId +
                ", lng=" + lng +
                ", lat=" + lat +
                ", webhook='" + webhook + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }
}
