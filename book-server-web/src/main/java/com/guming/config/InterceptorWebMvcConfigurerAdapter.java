package com.guming.config;

import com.guming.admin.interceptor.AuthenticationInterceptor;
import com.guming.admin.interceptor.JurisdictionInterceptor;
import com.guming.admin.interceptor.MdcInterceptor;
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
    public AuthenticationInterceptor authenticationInterceptor(){
        return new AuthenticationInterceptor();
    }

    @Bean
    public JurisdictionInterceptor jurisdictionInterceptor(){
        return new JurisdictionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/**","/swagger-resources/**","/v2/**","/client/**");
        registry.addInterceptor(jurisdictionInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/**","/swagger-resources/**","/v2/**","/client/**");
        registry.addInterceptor(mdcInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/**","/swagger-resources/**","/v2/**");
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
