package com.guming.common.annotation;


import com.guming.common.constants.business.OperationType;

import java.lang.annotation.*;

/**
 * @Author: PengCheng
 * @Description: 菜单权限认证注解，
 * @Date: 2018/4/12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Inherited
public @interface MenuOperateAuthority {

    //所属的菜单code，请查询菜单表
    String belongMenuCode();

    //所属的操作类型
    OperationType operationType();
}
