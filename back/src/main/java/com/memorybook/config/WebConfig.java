package com.memorybook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memorybook.intercptor.MemoAccessInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private final MemoAccessInterceptor memoAccessInterceptor;
	
	public WebConfig(MemoAccessInterceptor memoAccessInterceptor) {
		// TODO Auto-generated constructor stub
		this.memoAccessInterceptor = memoAccessInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(memoAccessInterceptor).addPathPatterns("/memo").excludePathPatterns("/oauth");
	}
	
	
}
