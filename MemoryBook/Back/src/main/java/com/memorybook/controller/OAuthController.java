package com.memorybook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memorybook.model.dto.KakaoUser;
import com.memorybook.model.provider.JwtTokenProvider;
import com.memorybook.model.service.KakaoApiService;
import com.memorybook.model.service.RefreshTokenService;
import com.memorybook.model.service.UserService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/oauth")
public class OAuthController {
	private final KakaoApiService kakaoApiService;
	private final UserService userService;
	private final RefreshTokenService refreshTokenService;
	private final JwtTokenProvider jwtTokenProvider;

	public OAuthController(KakaoApiService kakaoApiService, UserService userService,
			RefreshTokenService refreshTokenService, JwtTokenProvider jwtTokenProvider) {
		// TODO Auto-generated constructor stub
		this.kakaoApiService = kakaoApiService;
		this.userService = userService;
		this.refreshTokenService = refreshTokenService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Transactional
	@GetMapping("/kakao/callback")
	public ResponseEntity<?> kakaoLogin(@RequestParam("code") final String code) {
		// 프론트가 받은 인가 코드 -> 엑세스 토큰 발급을 카카오 서버에 요청한다. -> 엑세스 토큰으로 카카오 API 서버에 저장되어 있는 정보를
		// 요청함
		System.out.println("인가 코드: "+code);
		// 1. 카카오 서버에 엑세스 토큰 요청
		try {
			String kakaoAccessToken = kakaoApiService.requestAccessToken(code);
			// 카카오 서버에 액세스 토큰 요청 실패 시 예외 처리
			if (kakaoAccessToken == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to retrieve Kakao access token.");
			}
			System.out.println("카카오 엑세스 토큰: "+kakaoAccessToken);
			
			// 2. 엑세스 토큰으로 사용자 정보 요청
			KakaoUser kakaoUser = kakaoApiService.getUserInfo(kakaoAccessToken);
			// 카카오 사용자 정보가 없을 경우
			if (kakaoUser == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body("Failed to retrieve user information from Kakao.");
			}

			// 3. DB에서 사용자 확인 및 회원가입/ 로그인 처리
			String userId = userService.getUserId(kakaoUser.getKakao_account().getEmail());

			if (userId == null) {
				userId = userService.createUser(kakaoUser.getKakao_account().getEmail()).getUserId();
			}

			System.out.println("userId : " + userId);
			// 4. JWT 토큰 생성 -> access & refresh 2 가지로 나눠서 프론트로 보내줘야 함.

			String accessToken = jwtTokenProvider.createAccessToken(userId);
			String refreshToken = refreshTokenService.createAndSaveRefreshToken(userId);
			System.out.println("accessToken: "+ accessToken);
			System.out.println("refreshToken: "+refreshToken);
			// 5. JWT 반환
			ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken).httpOnly(true).secure(true)
					.path("/").maxAge(7 * 24 * 60 * 60).sameSite("Strict").build();
			// .JavaScript 접근 방지.HTTPS에서만 전달.쿠키 유효 경로 설정.쿠키 유효 기간 설정 (7일).CSRF 공격 방지
			
			return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshCookie.toString()).body(accessToken);
			// 리프레시 토큰 쿠키 설정, json 으로는 엑세스 토큰만 전달

		} catch (Exception e) {
			// 클라이언트에 500번 코드 노출하지 않도록 추후에 수정하기
			System.out.println("Error during Kakao login process"+ e);
			return ResponseEntity.status(500).body("카카오 로그인 처리 중 문제가 발생했습니다.");
		}
	}

	@GetMapping("/kakao/test")
	public ResponseEntity<?> loginTest(String jwt) {
		System.out.println(jwt);
		return ResponseEntity.ok(jwt);
	}
}
