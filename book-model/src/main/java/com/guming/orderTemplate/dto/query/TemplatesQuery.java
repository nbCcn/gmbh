package com.guming.orderTemplate.dto.query;

import com.guming.common.base.dto.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description: 订单模板分页查询用
 * @Date: 2018/4/20
 */
@Data
public class TemplatesQuery extends PageQuery {
    @ApiModelProperty(value = "是否有效")
    private Boolean isActive;

    @ApiModelProperty(value = "模板类型id")
    private Long templateTypeId;

    @ApiModelProperty(value = "所属仓库id")
    private Long wareHouseId;
}
