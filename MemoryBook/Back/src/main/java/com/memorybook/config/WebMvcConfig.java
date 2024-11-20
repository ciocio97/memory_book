package com.memorybook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memorybook.intercptor.Interceptor;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	private final Interceptor interceptor;
	
	public WebMvcConfig(Interceptor interceptor) {
		// TODO Auto-generated constructor stub
		this.interceptor = interceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(interceptor).addPathPatterns("").excludePathPatterns("/oauth");
	}
	
	
}
