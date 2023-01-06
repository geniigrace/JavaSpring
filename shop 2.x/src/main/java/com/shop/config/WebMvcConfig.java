package com.shop.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// 상품등록 : 절대경로와 상대경로를 매칭하기 위한 config
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")  //properties에서 설정한 uploadPath
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**").addResourceLocations(uploadPath);
        //images 로 시작하는 경우 uploadPath에 설정한 폴더를 기준으로 파일을 읽어오도록 설정
    }
}
