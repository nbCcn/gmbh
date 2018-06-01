package com.guming.dingtalk.request.personmsg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: Ccn
 * @Description:个人推送实体
 * @Date: 2018/6/1 14:23
 */
@Data
public class PersMsgPush {

    /**
     * 员工id列表（消息接收者，多个接收者用 | 分隔）
     */
    @JSONField(name = "touser")
    private String touser;


    /**
     * 企业应用id
     */
    @JSONField(name = "agentid")
    private String agentId = "175219166";

    /**
     * 消息类型
     */
    @JSONField(name = "msgtype")
    private String msgtype = "oa";

    /**
     * 消息字段
     */
    @JSONField(name = "oa")
    private Oa oa;
}
