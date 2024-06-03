package com.final_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 이 파일은 설정용으로 사용되는 파일입니다.꼭 적어줘야된다
public class WebMvcConfig implements WebMvcConfigurer {
    // 3000번 포트에서 들어오는 모든 요청에 대하여 CORS 설정을 적용하겠습니다.
    @Override   // Cors 교차 출처 리소스 공유 정책
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("OPTIONS", "GET", "PUT", "POST", "DELETE")
                .allowedHeaders("*");
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxUploadSize(52428800); // 50MB
        resolver.setMaxInMemorySize(10000000); // 10MB
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///D:/final-project-react/src/images/board/");
    }
}
