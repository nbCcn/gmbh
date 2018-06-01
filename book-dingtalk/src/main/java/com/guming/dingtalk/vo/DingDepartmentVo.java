package com.guming.dingtalk.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 钉钉开放平台返回的部门详情对象
 * 对于ISV来说这是个基本对象。ISV可以根据自己的业务来继承这个对象
 */
public class DingDepartmentVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //部门Ids
    private List<String> deptIdsList;

    public List<String> getDeptIdsList() {
        return deptIdsList;
    }

    public void setDeptIdsList(List<String> deptIdsList) {
        this.deptIdsList = deptIdsList;
    }
}
