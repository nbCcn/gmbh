package com.guming.dingtalk.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/7
 */
@Data
public class DingTalkResponseParam {
    private Integer errcode;

    private String errmsg;

    @JSONField(name = "expires_in")
    private Long expiresIn;
}
