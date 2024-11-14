package com.memorybook.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memorybook.mvc.model.dto.User;
import com.memorybook.mvc.model.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		// TODO Auto-generated constructor stub
		this.userService = userService;
	}
	
	@Transactional
	@PostMapping("")
	public ResponseEntity<?> login(@RequestBody User user) {
		//이메일을 받아서 아이디를 responseentity에 담아서 반환한다. 
		//서비스 로직에서 아이디 없으면 생성한다(uuid?)
		String email = user.getUserEmail();
		//올 때, JSON 데이터 KEY 이름 카멜케이스로 와야 함
		String userId = userService.getUserId(email);
		
		if(userId == null) {
			user = userService.createUser(email);
			userId = user.getUserId();
			return new ResponseEntity<>(userId, HttpStatus.CREATED);			
		}
		
		
		//아이디를 JWT 토큰에 담아서 JWT 헤더에 담아서 반환
		return new ResponseEntity<>(userId, HttpStatus.OK);
	}
	
}
