package com.memorybook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memorybook.model.dto.JwtToken;
import com.memorybook.model.dto.KakaoTokenResponse;
import com.memorybook.model.dto.KakaoUser;
import com.memorybook.model.provider.JwtTokenProvider;
import com.memorybook.model.service.KakaoApiService;
import com.memorybook.model.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/oauth")
public class OAuthController {
	private final KakaoApiService kakaoApiService;
	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;
	
	public OAuthController(KakaoApiService kakaoApiService, UserService userService, JwtTokenProvider jwtTokenProvider) {
		// TODO Auto-generated constructor stub
		this.kakaoApiService = kakaoApiService;
		this.userService = userService;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	@Transactional
	@GetMapping("/kakao/callback")
	public ResponseEntity<?> kakaoLogin(@RequestParam("code") final String code) {
		// 프론트가 받은 인가 코드 -> 엑세스 토큰 발급을 카카오 서버에 요청한다. -> 엑세스 토큰으로 카카오 API 서버에 저장되어 있는 정보를 요청함
		System.out.println(code);
		// 1. 카카오 서버에 엑세스 토큰 요청
		String accessToken = kakaoApiService.requestAccessToken(code);

		// 2. 엑세스 토큰으로 사용자 정보 요청
		KakaoUser kakaoUser = kakaoApiService.getUserInfo(accessToken);

		// 3. DB에서 사용자 확인 및 회원가입/ 로그인 처리
		String userId = userService.getUserId(kakaoUser.getKakao_account().getEmail());

		if (userId == null) {
			userId = userService.createUser(kakaoUser.getKakao_account().getEmail()).getUserId();
		}

		System.out.println("userId : "+ userId);
		// 4. JWT 토큰 생성 -> access & refresh 2 가지로 나눠서 프론트로 보내줘야 함.
		
		String jwtAccessToken = jwtTokenProvider.createAccessToken(userId);
		String jwtRefreshToken = jwtTokenProvider.createRefreshToken(userId);
		
		JwtToken token = new JwtToken(jwtAccessToken, jwtRefreshToken);
		
		// 5. JWT 반환
		System.out.println(token.toString());
		return ResponseEntity.ok(token);
	}
	
	@GetMapping("/kakao/test")
	public ResponseEntity<?> loginTest(String jwt){
		System.out.println(jwt);
		return ResponseEntity.ok(jwt);
	}
}
