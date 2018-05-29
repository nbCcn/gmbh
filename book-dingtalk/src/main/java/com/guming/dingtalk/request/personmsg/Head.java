package com.guming.dingtalk.request.personmsg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:    标题头
 * @Date: 2018/5/28
 */
@Data
public class Head {

    @JSONField(name = "bgcolor")
    private String bgcolor;

    @JSONField(name = "text")
    private String text;
}
