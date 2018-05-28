package com.guming.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/23 17:22
 */
public class StringToListUtils {


    /**
     * 字符串转为集合
     *
     * @param data
     * @return
     */
    @SuppressWarnings("all")
    public static List<String> parseList(String data) {

        if (!StringUtils.isEmpty(data)) {
            StringBuilder sb = new StringBuilder(data);
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length() - 1);
            String str = sb.toString();
            return Arrays.asList(str.split(","));

        }
        return null;
    }

    /**
     * 以，分割集合并返回字符串
     *
     * @param data
     * @return
     */
    public static String parseString(List<Long> data) {

        if (data.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Long id : data) {
                stringBuilder.append(id + ",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return stringBuilder.toString();

        }
        return null;
    }


}
