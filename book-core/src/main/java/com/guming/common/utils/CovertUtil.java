package com.guming.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/18
 */
public class CovertUtil {

    /**
     * beanlist转换器，可通过重写BeanResultCovertHandler来实现细节转换处理
     * @param entityList 要转换的实体结果集
     * @param clazz       要转成的数据类型
     * @param ignore      需要忽略的参数
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T,V> List<V> copyList(List<T> entityList, Class<V> clazz, String... ignore){
        List<V> resultList = new ArrayList<V>();
        try {
            if (entityList!=null && !entityList.isEmpty()){
                for (T entity : entityList) {
                    V result = clazz.newInstance();
                    BeanUtils.copyProperties(entity,result,ignore);
                    resultList.add(result);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
