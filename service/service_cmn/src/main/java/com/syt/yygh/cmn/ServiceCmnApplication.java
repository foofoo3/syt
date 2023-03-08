package com.syt.yygh.cmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: foofoo3
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.syt")
public class ServiceCmnApplication {
    public static void main(String[] args) {
            SpringApplication.run(ServiceCmnApplication.class, args);
        }
}
