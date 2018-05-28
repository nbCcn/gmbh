package com.guming.products.vo;

import com.guming.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/17
 */
@ApiModel(value = "ProductsClassifyVo",description = "商品分类返回参数")
public class ProductsClassifyVo {

    @ApiModelProperty(value = "商品分类id")
    private Long id;

    @ApiModelProperty(value = "商品分类名")
    private String name;

    @ApiModelProperty(value = "排序码")
    private Long order;

    private Date createdTime;

    @ApiModelProperty(value = "创建时间")
    private String createdTimeStr;

    private Date updatedTime;

    @ApiModelProperty(value = "更新时间")
    private String updatedTimeStr;

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

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedTimeStr() {
        if (this.createdTime!=null){
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
        if (this.updatedTime!=null){
            this.updatedTimeStr = DateUtil.formatDatetime(this.updatedTime);
        }
        return updatedTimeStr;
    }

    public void setUpdatedTimeStr(String updatedTimeStr) {
        this.updatedTimeStr = updatedTimeStr;
    }
}
