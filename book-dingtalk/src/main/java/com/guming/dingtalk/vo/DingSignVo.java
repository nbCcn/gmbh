package com.guming.dingtalk.vo;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/11
 */
public class DingSignVo {

    private String jsticket;

    private String signature;

    private String nonceStr;

    private String timeStamp;

    private String corpId;

    private String agentid;

    public String getJsticket() {
        return jsticket;
    }

    public void setJsticket(String jsticket) {
        this.jsticket = jsticket;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }
}
