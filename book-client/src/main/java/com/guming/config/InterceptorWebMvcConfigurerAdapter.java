package com.guming.config;

import com.guming.client.interceptor.ClientAuthInterceptor;
import com.guming.client.interceptor.JurisdictionInterceptor;
import com.guming.client.interceptor.MdcInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: PengCheng
 * @Description: 拦截器添加
 * @Date: 2018/4/11
 */
@Configuration
public class InterceptorWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Bean
    public MdcInterceptor mdcInterceptor(){
        return new MdcInterceptor();
    }

    @Bean
    public JurisdictionInterceptor jurisdictionInterceptor(){
        return new JurisdictionInterceptor();
    }

    @Bean
    public ClientAuthInterceptor clientAuthInterceptor(){
        return new ClientAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(clientAuthInterceptor()).addPathPatterns("/**").excludePathPatterns("/client/login/**");
        registry.addInterceptor(jurisdictionInterceptor()).addPathPatterns("/**").excludePathPatterns("/client/login/**","/swagger-resources/**","/v2/**");
        registry.addInterceptor(mdcInterceptor()).addPathPatterns("/**").excludePathPatterns("/client/login/**","/swagger-resources/**","/v2/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
