package com.guming.authority.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/11
 */
@Entity
@Table(name = "sys_menu")
public class Menu implements Serializable {
    private Long id;
    private String menuName;
    private String menuCode;
    private String menuUrl;
    private String pid;
    private String menuImg;
    private Integer menuOrder;
    private Date createDate;
    private Date updateDate;
    private Boolean isValid;
    private String i18NMenuName;
    private String operationType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "menu_name")
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Column(name = "menu_code",unique = true)
    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    @Column(name = "menu_url")
    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    @Column(name = "pid")
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Column(name = "menu_img")
    public String getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
    }

    @Column(name = "menu_order")
    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "update_date")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "is_valid")
    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    @Column(name = "i18n_menu_name")
    public String getI18NMenuName() {
        return i18NMenuName;
    }

    public void setI18NMenuName(String i18NMenuName) {
        this.i18NMenuName = i18NMenuName;
    }

    @Column(name = "operation_type")
    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
