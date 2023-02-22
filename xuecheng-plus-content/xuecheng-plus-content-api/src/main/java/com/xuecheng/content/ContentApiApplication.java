package com.xuecheng.content;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: com.xuecheng.ContentApiApplication
 * @Package: PACKAGE_NAME
 * @Description:
 * @Datetime: 2023/2/16   10:15
 * @Author: YuHan.Kang@outlook.com
 */
@SpringBootApplication
@EnableSwagger2Doc
public class ContentApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApiApplication.class, args);
    }
}
