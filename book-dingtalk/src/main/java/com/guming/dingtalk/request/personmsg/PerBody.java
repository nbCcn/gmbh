package com.guming.dingtalk.request.personmsg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description: 信息体
 * @Date: 2018/5/28
 */
@Data
public class PerBody {

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "form")
    private List<Form> formList;

    @JSONField(name = "rich")
    private Rich rich;

    @JSONField(name = "content")
    private String content;

    @JSONField(name = "image")
    private String image;

    @JSONField(name = "file_count")
    private String fileCount;

    @JSONField(name = "author")
    private String author;


}
