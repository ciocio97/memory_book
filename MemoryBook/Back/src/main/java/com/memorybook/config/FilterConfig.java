package com.memorybook.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.memorybook.filter.JwtAuthFilter;

@Configuration
public class FilterConfig {

	
	@Bean
	public FilterRegistrationBean<JwtAuthFilter> jwtFilter(JwtAuthFilter jwtFilter){
		FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
		
		registrationBean.setFilter(jwtFilter);
		registrationBean.addUrlPatterns("/memo/*"); //memo패턴에만 필터 적용
		registrationBean.setOrder(1); //우선순위 설정
		
		return registrationBean;
	}
}
