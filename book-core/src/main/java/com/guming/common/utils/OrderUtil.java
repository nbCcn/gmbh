package com.guming.common.utils;

import java.util.Date;

/**
 * @Author: PengCheng
 * @Description: 订单工具类
 * @Date: 2018/4/28
 */
public class OrderUtil {

    /**
     * 订单号生产
     * @return
     */
    public static String orderCodeGenerate(){
        int random = (int) (Math.random()*9000+1000);
        return DateUtil.formateDateDetail(new Date()) + random;
    }

    public static String versionGenerate(){
        return DateUtil.formateDateDetail(new Date());
    }
}
