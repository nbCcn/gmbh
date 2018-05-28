package com.guming.common.constants.business;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/4
 */
public enum LogisticsStatus {

    DISTRIBUTION(1,"配送","logistics.state.distribution"),
    LOGISTICS(2,"物流","logistics.state.logistics"),
    EXPRESS(3,"快递","logistics.state.express"),
    LIFTING(4,"自提","logistics.state.self.lifting");

    private Integer code;
    private String name;
    private String i18N;

    LogisticsStatus(Integer code, String name, String i18N) {
        this.code = code;
        this.name = name;
        this.i18N = i18N;
    }

    public static LogisticsStatus getLogisticsStatus(Integer code){
        for (LogisticsStatus o: LogisticsStatus.values()){
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
