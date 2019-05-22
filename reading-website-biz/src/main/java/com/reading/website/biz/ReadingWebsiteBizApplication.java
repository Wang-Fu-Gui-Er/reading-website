package com.reading.website.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ReadingWebsiteBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadingWebsiteBizApplication.class, args);
    }

}

