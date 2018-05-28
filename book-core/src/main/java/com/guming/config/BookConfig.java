package com.guming.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: PengCheng
 * @Description: 加载book.properties下的数据
 * @Date: 2018/5/9
 */
@Component
@ConfigurationProperties
@Data
@PropertySource("classpath:book.properties")
public class BookConfig {

    @Value("${user.initial.password}")
    private String initialPassword;

    @Value("${product.limit.stat}")
    private Boolean productLimitStat;

    @Value("${system.version}")
    private String systemVersion;

    @Value("${system.phone}")
    private String phone;

    @Value("{file.upload.temp.path}")
    private String  uploadTempPath;


}
