package com.guming.dingtalk.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/7
 */
@Data
public class DingUserIdResponseParam extends DingTalkResponseParam{

    private String userid;

    private String deviceId;

    @JSONField(name = "is_sys")
    private Boolean isSys;

    @JSONField(name = "sys_level")
    private Integer sysLevel;
}
