package com.guming.dingtalk.request.personmsg;

import com.alibaba.fastjson.annotation.JSONField;
import com.guming.dingtalk.request.personmsg.Body;
import com.guming.dingtalk.request.personmsg.Head;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description: 个人推送数据实体
 * @Date: 2018/5/28
 */
@Data
public class PersonMsgPush {

    /**
     * 详情url
     */
    @JSONField(name="message_url")
    private String messageUrl;

    /**
     * 标题头
     */
    @JSONField(name="head")
    private Head head;

    /**
     * 主体
     */
    @JSONField(name = "body")
    private Body body;

    /**
     * 内容
     */
    @JSONField(name = "content")
    private String content;

    /**
     * 图片
     */
    @JSONField(name = "image")
    private String image;

    /**
     * 附件数量
     */
    @JSONField(name = "file_count")
    private Integer fileCount;

    /**
     * 发送人
     */
    @JSONField(name = "author")
    private String author;


}
