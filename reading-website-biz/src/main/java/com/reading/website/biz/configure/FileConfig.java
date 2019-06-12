package com.reading.website.biz.configure;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * 文件上传大小配置
 *
 * @xyang010 2019/6/9
 */
@Configuration
public class FileConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(1024L * 1024L * 1024L);
        return factory.createMultipartConfig();
    }
}
