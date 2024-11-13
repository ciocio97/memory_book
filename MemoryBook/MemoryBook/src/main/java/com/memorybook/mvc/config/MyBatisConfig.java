package com.memorybook.mvc.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = "com.memorybook.mvc.model.dao")
@Configuration
public class MyBatisConfig {

}
