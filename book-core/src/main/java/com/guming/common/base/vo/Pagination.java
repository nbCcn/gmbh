package com.guming.common.base.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/13
 */
public class Pagination {

    @ApiModelProperty(value = "总数")
    private Long total;

    @ApiModelProperty(value = "当前页")
    private Integer current;

    @ApiModelProperty(value = "每页展示数")
    private Integer pageSize;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Pagination(Long total, Integer current, Integer pageSize) {
        this.total = total;
        this.current = current + 1;
        this.pageSize = pageSize;
    }
}
