package com.guming.dingtalk.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/7
 */
@Data
public class DingTokenResponseParam extends DingTalkResponseParam{
    @JSONField(name = "access_token")
    private String accessToken;
}
