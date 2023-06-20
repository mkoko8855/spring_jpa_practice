package com.spring.jpa;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){


        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("문서에 제목을 다는 곳 입니다. Spring Boot Board API Example")//info 객체를 통해 설명, 제목을 달 수 있겠다.
                        .description(("Spring Boot Board API 예시 프로젝트입니다."))
                        .version("v1.0.0")
                );
    }

}
