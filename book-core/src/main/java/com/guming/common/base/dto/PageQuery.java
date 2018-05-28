package com.guming.common.base.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description: 分页查询基础query
 * @Date: 15:23 2018/4/10/010
 */
public class PageQuery extends BaseQuery{
    @ApiModelProperty(value = "当前页（默认1）")
    private Integer page = 1;

    @ApiModelProperty(value = "每页大小（默认10）")
    private Integer pageSize = 10;

    public Integer getPage() {
        return page-1;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
