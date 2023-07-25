package com.app.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean   // swagger 설정의 핵심
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()   // ApiSelectorBuilder 생성
                .apis(RequestHandlerSelectors.basePackage("com.app.api"))   // API 패키지 경로 todo 패키지 경로 수정
                .paths(PathSelectors.ant("/api/**"))     //any: 모든 api 경로에 대해 문서화, ant: path 조건에 따라서 API 문서화
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API 문서")
                .description("API에 대해 설명해주는 API 문서입니다")
                .version("1.0")
                .build();
    }
}
