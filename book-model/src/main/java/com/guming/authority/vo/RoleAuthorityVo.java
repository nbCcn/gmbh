package com.guming.authority.vo;

import com.guming.authority.entity.RoleMenu;
import lombok.Data;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description: 角色权限对象
 * @Date: 2018/4/12
 */
@Data
public class RoleAuthorityVo {
    private Long id;
    private String roleName;
    private Byte isValid;
    private List<RoleMenu> roleMenuList;
}
