package com.guming.shops.dto.query;


import com.guming.common.base.dto.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/12 15:02
 */
@Data
@ApiModel(value = "ShopQuery", description = "店铺查询实体")
public class ShopQuery extends PageQuery {

    // 模糊条件字段
    @ApiModelProperty(value = "模糊条件")
    private String search;
    // 开始时间
    @ApiModelProperty(value = "开始时间")
    private String startTime;
    // 结束时间
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "店铺状态")
    private Integer status;
}
