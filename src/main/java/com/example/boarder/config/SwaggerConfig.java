package com.example.boarder.config;

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

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final String SPRING_JWT_HEADER;

    public SwaggerConfig(@Value("${spring.jwt.header}") String SPRING_JWT_HEADER) {
        this.SPRING_JWT_HEADER = SPRING_JWT_HEADER;
    }

    //api 정보를 담는 기본 틀
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BOARD REST API")
                .description("BOARD REST API Documentation")
                .version("1.0")
                .build();
    }

    //전체 접근 허용 스웨거, controller 패키지 매핑
    @Bean
    public Docket unAuthSwagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("permitAll API")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.boarder.controller"))
                .paths(PathSelectors.ant("/permitAll/**"))
                .build()
                .useDefaultResponseMessages(false);
    }
}
