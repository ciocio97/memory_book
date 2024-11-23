package com.memorybook.filter;

import java.io.IOException;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.memorybook.model.provider.JwtTokenProvider;
import com.memorybook.model.service.RefreshTokenService;
import com.memorybook.model.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;
	private final RefreshTokenService refreshTokenService;

	public JwtAuthFilter(JwtTokenProvider jwtTokenProvider, UserService userService, RefreshTokenService refreshTokenService) {
		// TODO Auto-generated constructor stub
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
		this.refreshTokenService = refreshTokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// CORS 프리플라이트 요청 처리
	    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
	        // CORS 프리플라이트 요청에 대해 허용 응답 설정
	        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
	        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	        response.setStatus(HttpServletResponse.SC_OK);
	        return;
	    }		
		
		String accessToken = jwtTokenProvider.resolveAccessToken(request);
		System.out.println("엑세스 토큰: "+accessToken);
		try {
			if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
				// Access Token is valid
				String userId = jwtTokenProvider.getUserIdFromToken(accessToken);
				if (!userService.existsbyId(userId)) {
					// 등록된 유저가 아님
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                    return;
				}
				request.setAttribute("userId", userId);
				System.out.println("엑세스 토큰 유효함: "+userId);
			} else if (accessToken == null || !jwtTokenProvider.validateToken(accessToken)) {
				// Access Token is expired -> 리프레시 토큰 검증 및 재발급
				String refreshToken = jwtTokenProvider.resolveRefreshTokenFromCookie(request);

				if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
					// refresh token is valid
					String userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
					System.out.println("리프레시 토큰 유효함: "+userId);
					if (userService.existsbyId(userId) && userId.equals(refreshTokenService.getUserIdByToken(refreshToken))) {
						// 새로운 엑세스 토큰 발급
						String newAccessToken = jwtTokenProvider.createAccessToken(userId);
						response.setHeader("Authorization", "Bearer " + newAccessToken);
						
						request.setAttribute("userId", userId);
					} else {
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Refresh Token");
                        return;
					}
				} else {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Refresh Token");
					return;
				}
			}
		} catch (Exception e) {
			// 유효성 검증 실패
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
			System.err.println(e);
			return;
		}

		// 검증 성공
		filterChain.doFilter(request, response);
	}

}
