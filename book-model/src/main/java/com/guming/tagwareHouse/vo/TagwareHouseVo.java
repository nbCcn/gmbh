package com.guming.tagwareHouse.vo;

import com.guming.common.utils.DateUtil;
import com.guming.tagline.vo.TagLineVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@ApiModel(value = "TagwareHouseVo", description = "返回的仓库信息实体")
public class TagwareHouseVo {
    @ApiModelProperty(value = "仓库Id")
    private Long id;
    @ApiModelProperty(value = "仓库名称")
    private String name;
    @ApiModelProperty(value = "分组")
    private Integer groupId;
    @ApiModelProperty(value = "经度")
    private Double lng;
    @ApiModelProperty(value = "纬度")
    private Double lat;
    @ApiModelProperty(value = "webhook")
    private String webhook;

    @ApiModelProperty(value = "创建时间")
    private String createdTimeStr;
    private Date createdTime;

    @ApiModelProperty(value = "修改时间")
    private String updatedTimeStr;
    private Date updatedTime;

    @ApiModelProperty(value = "路线信息组")
    private List<TagLineVo> taglineListVo;

    public List<TagLineVo> getTaglineListVo() {
        return taglineListVo;
    }

    public void setTaglineListVo(List<TagLineVo> taglineListVo) {
        this.taglineListVo = taglineListVo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
        this.webhook = webhook == null ? null : webhook.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatedTimeStr() {
        if (this.createdTime != null) {
            this.createdTimeStr = DateUtil.formatDate(this.createdTime);
        }
        return createdTimeStr;
    }

    public void setCreatedTimeStr(String createdTimeStr) {
        this.createdTimeStr = createdTimeStr;
    }

    public String getUpdatedTimeStr() {
        if (this.updatedTime != null) {
            this.updatedTimeStr = DateUtil.formatDate(this.createdTime);
        }
        return updatedTimeStr;
    }

    public void setUpdatedTimeStr(String updatedTimeStr) {
        this.updatedTimeStr = updatedTimeStr;
    }

    @Override
    public String toString() {
        return "TagwareHouseVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", groupId=" + groupId +
                ", lng=" + lng +
                ", lat=" + lat +
                ", webhook='" + webhook + '\'' +
                ", createdTime=" + createdTime +
                ", createdTimeStr='" + createdTimeStr + '\'' +
                ", updatedTime=" + updatedTime +
                ", updatedTimeStr='" + updatedTimeStr + '\'' +
                ", taglineListVo=" + taglineListVo +
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
        TagwareHouseVo that = (TagwareHouseVo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(groupId, that.groupId) &&
                Objects.equals(lng, that.lng) &&
                Objects.equals(lat, that.lat) &&
                Objects.equals(webhook, that.webhook) &&
                Objects.equals(createdTime, that.createdTime) &&
                Objects.equals(createdTimeStr, that.createdTimeStr) &&
                Objects.equals(updatedTime, that.updatedTime) &&
                Objects.equals(updatedTimeStr, that.updatedTimeStr) &&
                Objects.equals(taglineListVo, that.taglineListVo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, groupId, lng, lat, webhook, createdTime, createdTimeStr, updatedTime, updatedTimeStr, taglineListVo);
    }
}