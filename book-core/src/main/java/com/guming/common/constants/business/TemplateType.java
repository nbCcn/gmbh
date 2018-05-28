package com.guming.common.constants.business;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/4
 */
public enum TemplateType {

    PREPARATION(1,"筹备模板",""),
    NINGMENG(2,"柠檬模板",""),
    COMMON(3,"正常模板","");

    private Integer code;
    private String name;
    private String i18N;

    TemplateType(Integer code, String name, String i18N) {
        this.code = code;
        this.name = name;
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

    public String getI18N() {
        return i18N;
    }

    public void setI18N(String i18N) {
        this.i18N = i18N;
    }
}
