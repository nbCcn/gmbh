package com.guming.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guming.common.base.vo.MapVo;
import com.guming.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/25
 */
@ApiModel(value = "OrderVo",description = "订单返回信息实体")
public class OrderVo {

    @Setter
    @Getter
    @ApiModelProperty(value = "订单id")
    private Long id;

    @Setter
    @Getter
    @ApiModelProperty(value = "订单编号")
    private String code;

    @Setter
    @Getter
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @Setter
    @Getter
    @ApiModelProperty(value = "收货店铺id")
    private Long sendShopId;

    @Setter
    @Getter
    @ApiModelProperty(value = "收货地址")
    private String address;

    @Setter
    @Getter
    @ApiModelProperty(value = "店铺名")
    private String shopName;

    @Setter
    @Getter
    @ApiModelProperty(value = "订单模板名")
    private String tempName;

    @Setter
    @Getter
    @ApiModelProperty(value = "订单类型")
    private MapVo tempTypeMapVo;

    @Setter
    @Getter
    @ApiModelProperty(value = "订单状态")
    private MapVo statusMapVo;

    @Setter
    @Getter
    @ApiModelProperty(value = "运送方信息")
    private MapVo tagLineMapVo;

    @Setter
    @Getter
    @ApiModelProperty(value = "仓库信息")
    private MapVo tagWarehouseMapVo;

    @Setter
    @Getter
    @ApiModelProperty(value = "配送方式")
    private MapVo shippingMapVo;

    @Setter
    @Getter
    @ApiModelProperty(value = "配送负责人")
    private String shippingPeople;

    @JsonIgnore
    @Setter
    @Getter
    private Date sendTime;

    @Setter
    @ApiModelProperty(value = "送货时间")
    private String sendTimeStr;

    @Setter
    @Getter
    @ApiModelProperty(value = "总价")
    private BigDecimal totalPrice;

    @Setter
    @Getter
    @JsonIgnore
    private Date createTime;

    @Setter
    @ApiModelProperty(value = "创建时间")
    private String createTimeStr;

    @Setter
    @Getter
    @JsonIgnore
    private Date updateTime;

    @Setter
    @ApiModelProperty(value = "更新时间")
    private String updateTimeStr;

    @Setter
    @Getter
    @ApiModelProperty(value = "订单关联商品信息实体组")
    private List<OrderTemplateVo> orderTemplateVoList;

    @Setter
    @Getter
    @ApiModelProperty(value = "订单是否可撤回")
    private Boolean isRevoke =false;

    @Setter
    @Getter
    @ApiModelProperty(value = "订单是否已完成")
    private Boolean isFinish = false;

    public String getSendTimeStr() {
        if(this.sendTime != null){
            this.sendTimeStr = DateUtil.formatDate(this.sendTime);
        }
        return sendTimeStr;
    }

    public String getUpdateTimeStr() {
        if(this.updateTime != null){
            this.updateTimeStr = DateUtil.formatDatetime(this.updateTime);
        }
        return updateTimeStr;
    }

    public String getCreateTimeStr() {
        if(this.createTime != null){
            this.createTimeStr = DateUtil.formatDatetime(this.createTime);
        }
        return createTimeStr;
    }
}
