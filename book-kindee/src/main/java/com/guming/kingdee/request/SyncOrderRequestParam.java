package com.guming.kingdee.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guming.common.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/14
 */
public class SyncOrderRequestParam {

    /**
     * 订单编码
     */
    @Setter
    @Getter
    @JSONField(name = "num")
    private String orderCode;

    /**
     * 店铺编号
     */
    @Setter
    @Getter
    @JSONField(name = "shop_id")
    private String shopCode;

    /**
     * 推送时间(默认当期时间)
     */
    @Setter
    @Getter
    @JsonIgnore
    private Date pubDate = new Date();

    @Setter
    @JSONField(name = "pub_date")
    private String pubDateStr;

    /**
     * 配送时间
     */
    @JsonIgnore
    @Setter
    @Getter
    private Date sendDate;

    @JSONField(name = "send_date")
    @Setter
    private String sendDateStr;

    /**
     * 配送方式（默认为0）
     */
    @JSONField(name = "logistics")
    @Setter
    @Getter
    private Integer logistics=0;

    /**
     * 订单内容
     */
    @JSONField(name = "content")
    @Setter
    @Getter
    private String content;

    /**
     * 订单中商品的数据
     */
    @JSONField(name = "items")
    @Setter
    @Getter
    private List<InventoryProductRequestParam> inventoryProductRequestParamList;

    public String getPubDateStr() {
        if (this.pubDate != null){
            this.pubDateStr = DateUtil.formatDatetime(this.pubDate);
        }
        return pubDateStr;
    }

    public String getSendDateStr() {
        if (this.sendDate != null){
            this.sendDateStr = DateUtil.formatDatetime(this.sendDate);
        }
        return sendDateStr;
    }
}
