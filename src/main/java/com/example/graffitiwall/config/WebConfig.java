package com.example.graffitiwall.config;

import com.example.graffitiwall.web.interceptor.BoardLoginCheckInterceptor;
import com.example.graffitiwall.web.interceptor.PostitLoginCheckInterceptor;
import com.example.graffitiwall.web.interceptor.UserLoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/api/v1/users/**")
                .excludePathPatterns("/api/v1/users", "/api/v1/users/**/duplicate");

        registry.addInterceptor(new BoardLoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/api/v1/boards/**");

        registry.addInterceptor(new PostitLoginCheckInterceptor())
                .order(3)
                .addPathPatterns("/api/v1/postit/**");
    }
}
