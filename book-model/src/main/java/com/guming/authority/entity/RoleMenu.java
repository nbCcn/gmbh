package com.guming.authority.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: PengCheng
 * @Description: 角色菜单权限表
 * @Date: 2018/4/11
 */
@Entity
@Table(name = "sys_role_menu")
public class RoleMenu implements Serializable {
    private Long id;
    private Long roleId;
    private String menuCode;
    private Integer menuOperation;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "role_id")
    private Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Column(name = "menu_code")
    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    @Column(name = "menu_operation")
    public Integer getMenuOperation() {
        return menuOperation;
    }

    public void setMenuOperation(Integer menuOperation) {
        this.menuOperation = menuOperation;
    }

}
