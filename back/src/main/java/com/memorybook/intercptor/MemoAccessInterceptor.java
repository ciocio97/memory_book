package com.memorybook.intercptor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MemoAccessInterceptor implements HandlerInterceptor{
	
	@Value("${memo.open.time}")
	private String openTimeConfig;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
		ZonedDateTime openTime = ZonedDateTime.parse(openTimeConfig, DateTimeFormatter.ISO_ZONED_DATE_TIME);
		
		if(request.getMethod().equals("GET") && request.getRequestURI().equals("/memo")) {
			if(now.isBefore(openTime)) {
				//아직 메모가 열리는 날이 아님
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().write("아직 오픈 시간이 아닙니다.");
				System.out.println("not open yet");
				return false; //요청 차단
			}
		}
		
		return true; //요청 진행
	}

}
