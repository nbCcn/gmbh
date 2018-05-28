package com.guming.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/7
 */
@Component
@ConfigurationProperties(prefix = "ding.config")
@PropertySource("classpath:ding.properties")
public class DingTalkConfig {

    private String agentId;

    private String corpId;

    private String corpSecret;

    private String tokenUrl;

    private String jsapiUrl;

    private String userUrl;

    private String userInfoUrl;

    private String msgUrl;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getJsapiUrl() {
        return jsapiUrl;
    }

    public void setJsapiUrl(String jsapiUrl) {
        this.jsapiUrl = jsapiUrl;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    public String getMsgUrl() {
        return msgUrl;
    }

    public void setMsgUrl(String msgUrl) {
        this.msgUrl = msgUrl;
    }
}



