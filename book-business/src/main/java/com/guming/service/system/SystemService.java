package com.guming.service.system;


import com.guming.common.base.service.BaseService;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/10
 */
public interface SystemService extends BaseService {

    /**
     * 获取系统版本
     * @return
     */
    String getVersion();

    /**
     * 获取联系电话
     * @return
     */
    String getContactNumber();
}
