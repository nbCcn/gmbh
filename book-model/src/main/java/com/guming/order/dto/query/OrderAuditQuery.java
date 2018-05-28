package com.guming.order.dto.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/24
 */
@Data
@ApiModel(value = "OrderAuditQuery",description = "订单审核实体")
public class OrderAuditQuery extends OrderQuery{

    @ApiModelProperty(value = "订单id")
    private Long orderId;
}
