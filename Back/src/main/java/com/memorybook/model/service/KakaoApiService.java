package com.memorybook.model.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.memorybook.model.dto.KakaoTokenResponse;
import com.memorybook.model.dto.KakaoUser;

@Service
public class KakaoApiService {

	private final String clientId;
	private final String redirectUri;
//	private final String clientSecret;
	private final RestTemplate restTemplate;

	public KakaoApiService(@Value("${kakao.clientId}") String clientId,
			@Value("${kakao.redirectUri}") String redirectUri,
			RestTemplate restTemplate) {
		// TODO Auto-generated constructor stub
		this.clientId = clientId;
		this.redirectUri = redirectUri;
		this.restTemplate = restTemplate;
//		this.clientSecret = clientSecret;
	}

	public String requestAccessToken(String code) {
		// 인가코드로 엑세스토큰 요청하는 메소드
		String url = "https://kauth.kakao.com/oauth/token";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "authorization_code");
		formData.add("client_id", clientId);
		formData.add("redirect_uri", redirectUri);
		formData.add("code", code);
//		formDate.add("client_secret", clientSecret);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

		try {
			ResponseEntity<KakaoTokenResponse> response = restTemplate.exchange(url, HttpMethod.POST, request,
					KakaoTokenResponse.class); // rest 요청 보내기

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				return response.getBody().getAccessToken();
			}
		} catch (HttpClientErrorException e) {
			// 에러 로그
			System.err
					.println("Error during token request: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
		} catch (Exception e) {
			// 기타 예외 처리
			System.err.println("Unexpected error: " + e.getMessage());
		}
		return null;
//		return accessToken;
	}

	public KakaoUser getUserInfo(String accessToken) {
		// TODO Auto-generated method stub

		String url = "https://kapi.kakao.com/v2/user/me";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		System.out.println(accessToken);
		HttpEntity<?> request = new HttpEntity<>(headers);
		try {
			ResponseEntity<KakaoUser> response = restTemplate.exchange(url, HttpMethod.GET, request, KakaoUser.class);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			System.err.println("Error during user info request: " + e.getResponseBodyAsString());
			throw new RuntimeException("Failed to fetch user info: " + e.getMessage());
		}
	}
}
