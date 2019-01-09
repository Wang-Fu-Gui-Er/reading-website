package com.reading.website.biz.configure;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  MapperScannerConfigurer  配置mapper
 *
 * @yx8102    2019/1/7
 */
@Configuration
@AutoConfigureAfter(MyBatisModelConfig.class)
public class MyBatisMapperScannerConfig {
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setSqlSessionFactoryBeanName("sqlSessionFactory");
        msc.setBasePackage("com.reading.website.biz.mapper");
        return msc;
    }
}