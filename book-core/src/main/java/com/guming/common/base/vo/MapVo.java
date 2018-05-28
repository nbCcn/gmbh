package com.guming.common.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/18
 */
@ApiModel(value = "MapVo",description = "用于下拉框的实体参数")
public class MapVo extends BaseVo{

    @ApiModelProperty(value = "key")
    private Long id;

    @ApiModelProperty(value = "value")
    private String name;

    public MapVo() {
    }

    public MapVo(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
