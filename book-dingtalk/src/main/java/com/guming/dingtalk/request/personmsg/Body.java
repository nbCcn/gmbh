package com.guming.dingtalk.request.personmsg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:    信息体
 * @Date: 2018/5/28
 */
@Data
public class Body {

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "form")
    private List<Form> formList;

    @JSONField(name = "rich")
    private Rich rich;
}
