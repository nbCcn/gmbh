package com.guming.orderTemplate.vo;

import com.guming.common.base.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:`
 * @Date: 2018/4/20
 */
public class TemplatesTypeVo extends BaseVo {
    private Long id;

    @ApiModelProperty(value = "分类名")
    private String name;

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
}
