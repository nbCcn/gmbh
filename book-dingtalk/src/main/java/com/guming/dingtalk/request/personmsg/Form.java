package com.guming.dingtalk.request.personmsg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:    推送消息表单
 * @Date: 2018/5/28
 */
@Data
public class Form {

    @JSONField(name = "key")
    private String key;

    @JSONField(name = "value")
    private String value;
}
