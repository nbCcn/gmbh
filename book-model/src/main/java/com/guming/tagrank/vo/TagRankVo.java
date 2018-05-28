package com.guming.tagrank.vo;

import com.guming.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/18 14:17
 */
@ApiModel(value = "TagRankVo", description = "返回的等级信息实体")
public class TagRankVo {
    @ApiModelProperty(value = "等级Id")
    private Long id;
    @ApiModelProperty(value = "等级名称")
    private String name;
    @ApiModelProperty(value = "是否筹备")
    private Integer isPrepare;
    @ApiModelProperty(value = "排序码")
    private Integer orderCode;

    @ApiModelProperty(value = "店铺Id")
    private String createdTimeStr;
    private Date createdTime;
    @ApiModelProperty(value = "店铺Id")
    private String updatedTimeStr;
    private Date updatedTime;

    @Override
    public String toString() {
        return "TagRankVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isPrepare=" + isPrepare +
                ", orderCode=" + orderCode +
                ", createdTime=" + createdTime +
                ", createdTimeStr='" + createdTimeStr + '\'' +
                ", updatedTime=" + updatedTime +
                ", updatedTimeStr='" + updatedTimeStr + '\'' +
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
        TagRankVo that = (TagRankVo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(isPrepare, that.isPrepare) &&
                Objects.equals(orderCode, that.orderCode) &&
                Objects.equals(createdTime, that.createdTime) &&
                Objects.equals(createdTimeStr, that.createdTimeStr) &&
                Objects.equals(updatedTime, that.updatedTime) &&
                Objects.equals(updatedTimeStr, that.updatedTimeStr);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, isPrepare, orderCode, createdTime, createdTimeStr, updatedTime, updatedTimeStr);
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
        this.name = name;
    }

    public Integer getIsPrepare() {
        return isPrepare;
    }

    public void setIsPrepare(Integer isPrepare) {
        this.isPrepare = isPrepare;
    }

    public Integer getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Integer orderCode) {
        this.orderCode = orderCode;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedTimeStr() {
        if (this.createdTime != null) {
            this.createdTimeStr = DateUtil.formatDatetime(this.createdTime);
        }
        return createdTimeStr;
    }

    public void setCreatedTimeStr(String createdTimeStr) {
        this.createdTimeStr = createdTimeStr;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedTimeStr() {
        if (this.updatedTime != null) {
            this.updatedTimeStr = DateUtil.formatDatetime(this.updatedTime);
        }
        return updatedTimeStr;
    }

    public void setUpdatedTimeStr(String updatedTimeStr) {
        this.updatedTimeStr = updatedTimeStr;
    }
}
