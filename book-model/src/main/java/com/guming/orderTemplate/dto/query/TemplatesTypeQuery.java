package com.guming.orderTemplate.dto.query;

import com.guming.common.base.dto.PageQuery;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description: 订单模板分类分页查询用
 * @Date: 2018/4/20
 */
public class TemplatesTypeQuery extends PageQuery {
    @ApiModelProperty(value = "类型名")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
