package com.guming.common.constants.business;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/4
 */
public enum ShopStatus {

    CLOSE(0, "关闭", "shop.level.close"),
    PREPARATION(1, "筹备", "shop.level.preparation"),
    COMMON(2, "正常", "shop.level.common");

    private Integer code;
    private String name;
    private String i18N;

    ShopStatus(Integer code, String name, String i18N) {
        this.code = code;
        this.name = name;
        this.i18N = i18N;
    }

    public static ShopStatus getShopStatus(Integer code) {
        for (ShopStatus o : ShopStatus.values()) {
            if (o.getCode() == code) {
                return o;
            }
        }
        return null;
    }

    public static Integer getStatusCode(String name) {
        for (ShopStatus o : ShopStatus.values()) {
            if (o.getName().equals(name) || o.getName() == name) {
                return o.getCode();
            }
        }
        return null;
    }

    public String getI18N() {
        return i18N;
    }

    public void setI18N(String i18N) {
        this.i18N = i18N;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
