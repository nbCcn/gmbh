package com.guming.order.vo;

import com.guming.common.base.vo.BaseVo;
import com.guming.order.dto.query.OrderQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:    查询参数返回的订单实体
 * @Date: 2018/5/25
 */
@Data
@ApiModel(value = "OrderQueryVo",description = "查询参数返回的订单实体")
public class OrderQueryVo extends BaseVo {

    @ApiModelProperty(value = "订单数据实体")
    private OrderVo orderVo;

    @ApiModelProperty(value = "订单查询参数实体")
    private OrderQuery orderQuery;
}
