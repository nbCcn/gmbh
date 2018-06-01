package com.guming.dingtalk.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/11
 */
@Data
public class DingSignVo {

    private String jsticket;

    private String signature;

    private String nonceStr;

    private String timeStamp;

    private String corpId;

    private String agentid;
}
