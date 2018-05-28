package com.guming.order.dto.query;

import com.guming.common.base.dto.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/25
 */
@Data
@ApiModel(value = "OrderQuery",description = "订单返回参数实体")
public class OrderQuery extends PageQuery {

    @ApiModelProperty(value = "订单类型id")
    private Long tempTypeId;

    @ApiModelProperty(value = "订单状态")
    private Integer status;

    @ApiModelProperty(value = "订单编号")
    private String code;

    @ApiModelProperty(value = "仓库id")
    private Long wareHouseId;

    @ApiModelProperty(value = "线路id")
    private Long taglineId;

    @ApiModelProperty(value = "店铺名")
    private String shopName;

    @ApiModelProperty(value = "用户id",hidden = true)
    private Long userId;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;
}

