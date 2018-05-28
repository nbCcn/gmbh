package com.guming.kingdee.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/14
 */
@Data
public class InventoryResponseParam {

    @JSONField(name = "errcode")
    private Integer errCode;

    @JSONField(name = "kucun")
    private List<InventoryProductResponseParam> productList;
}
