package com.guming.kingdee.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/25
 */
@Data
public class SynOrderResponseParam {

    @JSONField(name = "errcode")
    private Integer errCode;

    @JSONField(name = "error")
    private Integer error;

    @JSONField(name = "kucun")
    private List<InventoryProductResponseParam> productList;
}
