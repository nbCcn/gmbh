package com.guming.common.constants.business;

/**
 * @Author: PengCheng
 * @Description: 菜单操作类型
 * @Date: 2018/4/11
 */
public enum OperationType {
    ADD(1,"add","operation.add"),
    EDIT(2,"edit","operation.edit"),
    DELETE(3,"delete","operation.delete"),
    LOOK(4,"look","operation.look") ,
    IMPORT(5,"import","operation.import"),
    EXPORT(6,"export","operation.export"),
    UNDERCARRIAGE(7,"undercarriage","operation.undercarriage");

    private Integer type;

    private String interactiveValue;

    private String i18nName;

    /**
     *
     * @param type                   类型值
     * @param interactiveValue     与前台的交互参数
     * @param i18nName              用于展示数的国际化
     */
    OperationType(int type, String interactiveValue, String i18nName) {
        this.type = type;
        this.interactiveValue = interactiveValue;
        this.i18nName = i18nName;
    }

    public static String getInteractiveValue(Integer type){
        for (OperationType o: OperationType.values()){
            if (o.getType()==type){
                return o.getInteractiveValue();
            }
        }
        return null;
    }

    public static String getI18nName(Integer type){
        for (OperationType o: OperationType.values()){
            if (o.getType()==type){
                return o.getI18nName();
            }
        }
        return null;
    }

    public static Integer getType(String interactiveValue){
        for (OperationType o: OperationType.values()){
            if (o.getInteractiveValue().equals(interactiveValue)){
                return o.getType();
            }
        }
        return null;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInteractiveValue() {
        return interactiveValue;
    }

    public void setInteractiveValue(String interactiveValue) {
        this.interactiveValue = interactiveValue;
    }

    public String getI18nName() {
        return i18nName;
    }

    public void setI18nName(String i18nName) {
        this.i18nName = i18nName;
    }
}
