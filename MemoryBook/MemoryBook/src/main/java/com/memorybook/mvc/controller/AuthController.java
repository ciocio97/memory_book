package com.memorybook.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("")
public class AuthController {
	@GetMapping("/auth/login/kakao")
	public ResponseEntity<?> kakaoLogin(@RequestParam("code") String accessCode, HttpServletResponse response) {
		return new ResponseEntity<>(null);
	}
	
}
