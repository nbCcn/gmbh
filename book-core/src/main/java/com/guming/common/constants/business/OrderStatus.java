package com.guming.common.constants.business;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/4
 */
public enum  OrderStatus {

    UNSUBMITTED(1,"未提交","order.status.unsubmitted"),
    SUBMITTED(2,"已提交","order.status.submission"),
    AUDITED(3,"已审核","order.status.audited"),
    DELIVERED(4,"已发货","order.status.delivered"),
    COMPLETE(5,"已完成","order.status.completed");

    private Integer code;
    private String name;
    private String i18N;

    OrderStatus(Integer code, String name, String i18N) {
        this.code = code;
        this.name = name;
        this.i18N = i18N;
    }

    public static OrderStatus getOrderStatus(Integer code){
        for (OrderStatus o: OrderStatus.values()){
            if (o.getCode()==code){
                return o;
            }
        }
        return null;
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

    public String getI18N() {
        return i18N;
    }

    public void setI18N(String i18N) {
        this.i18N = i18N;
    }
}
