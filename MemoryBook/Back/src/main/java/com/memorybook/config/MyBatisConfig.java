package com.memorybook.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = "com.memorybook.model.dao")
@Configuration
public class MyBatisConfig {

}
