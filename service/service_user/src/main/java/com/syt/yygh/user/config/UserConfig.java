package com.syt.yygh.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: foofoo3
 */
@Configuration
@MapperScan("com.syt.yygh.user.mapper")
public class UserConfig {
}
