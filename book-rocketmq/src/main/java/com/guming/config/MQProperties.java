package com.guming.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: Ccn
 * @Description:配置参数
 * @Date: 2018/5/30 11:50
 */
@Data
@ConfigurationProperties(prefix = "spring.rocketmq")
public class MQProperties {
    /**
     * config name server address
     */
    private String nameServerAddress;
    /**
     * config producer group , default to DPG+RANDOM UUID like DPG-fads-3143-123d-1111
     */
    private String producerGroup;
    /**
     * config send message timeout
     */
    private Integer sendMsgTimeout;
    /**
     * switch of trace message consumer: send message consumer info to topic: rmq_sys_TRACE_DATA
     */
    private Boolean traceEnabled;

    /**
     * switch of send message with vip channel
     */
    private Boolean vipChannelEnabled;

}
