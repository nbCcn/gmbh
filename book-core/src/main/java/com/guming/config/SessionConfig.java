package com.guming.config;

import com.guming.common.constants.SessionConstants;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/30
 */
@Configuration
public class SessionConfig {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                //单位为S
                container.setSessionTimeout(SessionConstants.SESSION_EXPIRE);
            }
        };
    }
}
