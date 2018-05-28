package com.guming.common.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/14
 */
@ApiModel(value = "PermissionVo",description = "详细操作实体")
@Data
public class PermissionVo {

    @ApiModelProperty(value = "前端使用的操作对象")
    private String interactiveValue;

    @ApiModelProperty(value = "操作方法名")
    private String name;
}
