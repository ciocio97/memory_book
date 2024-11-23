package com.memorybook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true); // 인증 정보 허용
		config.addAllowedOrigin("http://localhost:3000"); // Vue.js 클라이언트 도메인 허용
		config.addAllowedHeader("*"); // 모든 헤더 허용
		config.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
		config.addExposedHeader("Authorization"); // 응답 헤더 노출

		source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 CORS 설정 적용
		return new CorsFilter(source);
	}
}
