package com.guming.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 23:27 2018/4/19/019
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Value("${swagger2.service.title}")
    private String serviceTitle;

    @Value("${swagger2.service.description}")
    private String serviceDescription;

    @Value("${swagger2.service.serviceUrl}")
    private String serviceServiceUrl;

    @Value("${swagger2.service.version}")
    private String serviceVersion;

    @Bean
    public Docket serviceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.guming"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(serviceTitle)
                .description(serviceDescription)
                .termsOfServiceUrl(serviceServiceUrl)
                .version(serviceVersion)
                .build();
    }
}
