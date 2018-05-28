package com.guming.kingdee.request;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/14
 */
public class InventoryRequestParam {

    private List<InventoryProductRequestParam> items;

    public List<InventoryProductRequestParam> getItems() {
        return items;
    }

    public void setItems(List<InventoryProductRequestParam> items) {
        this.items = items;
    }
}
