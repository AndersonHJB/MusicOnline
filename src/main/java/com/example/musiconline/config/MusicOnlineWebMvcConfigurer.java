package com.example.musiconline.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置文件上传路径
 */
@Configuration
public class MusicOnlineWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${file.upload.dic}")
    private String fileUploadDic;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + fileUploadDic);
    }
}