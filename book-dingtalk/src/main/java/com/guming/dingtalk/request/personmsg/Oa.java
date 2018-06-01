package com.guming.dingtalk.request.personmsg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/6/1 14:39
 */
@Data
public class Oa {

    /**
     * 详情url
     */
    @JSONField(name = "message_url")
    private String messageUrl;

    /**
     * 标题头
     */
    @JSONField(name = "head")
    private Head head;

    /**
     * 主体
     */
    @JSONField(name = "body")
    private PerBody perBody;

}
