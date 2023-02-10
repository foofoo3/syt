package com.syt.yygh.hosp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: foofoo3
 */

@Configuration
@MapperScan("com.syt.yygh.hosp.mapper")
public class HospConfig {
}
