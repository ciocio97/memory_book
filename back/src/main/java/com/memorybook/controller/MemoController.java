package com.memorybook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memorybook.model.dto.Memo;
import com.memorybook.model.provider.JwtTokenProvider;
import com.memorybook.model.service.MemoService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/memo")
public class MemoController {
	private final MemoService memoService;
	private final JwtTokenProvider jwtTokenProvider;

	public MemoController(MemoService memoService, JwtTokenProvider jwtTokenProvider,
			JwtTokenProvider jwtTokenProvider2) {
		// TODO Auto-generated constructor stub
		this.memoService = memoService;
		this.jwtTokenProvider = jwtTokenProvider2;
	}

	@Transactional
	@GetMapping("")
	public ResponseEntity<?> getMemos(HttpServletRequest request) {
		try {
			String reader = (String) request.getAttribute("userId");
			if (reader == null) {
				System.out.println("Unauthorized access attempt");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
			}
			List<Map<String, Object>> memos = memoService.getMemosByReader(reader);
			if (memos == null) {
				// 받은 메모가 없음 -> 어떻게 처리할지??
				System.out.println("No memos found for userId: " + reader);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No memos found");
			}
			// 받은 메모들을 보내줌.
			return ResponseEntity.ok(memos);

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		} catch (Exception e) {
			System.out.println("Error retrieving memos");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving memos");
		}
	}
	
	@Transactional
	@GetMapping("/detail/{memoid}")
	public ResponseEntity<?> getMemoDetail(@PathVariable("memoid") String memoId, HttpServletRequest request) {
		try {
			String reader = (String) request.getAttribute("userId");
			if (reader == null) {
				System.out.println("Unauthorized access attempt");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
			}
			Memo memo = memoService.getMemoById(memoId);
			if (memo == null || !memo.getReader().equals(reader)) {
				// 받은 메모가 없음 -> 어떻게 처리할지??
				System.out.println("No memo found for memoId: " + memoId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No memo found");
			}
			// 받은 메모 보내줌.
			return ResponseEntity.ok(memo);

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		} catch (Exception e) {
			System.out.println("Error retrieving memos");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving memos");
		}
	}

	@Transactional
	@PostMapping("")
	public ResponseEntity<?> sendMemo(@RequestBody Map<String, String> memoMap, HttpServletRequest request) {
		try {
			String writer = (String) request.getAttribute("userId");
			if (writer == null) {
				System.out.println("Unauthorized access attempt to write memo");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
			}
			
			String reader = memoMap.get("linked_token"); //일단은 linked token을 넣는다.
			if(reader != null) {
				reader = jwtTokenProvider.getUserIdFromLinkedToken(reader);
			}

			int memoId = memoService.writeMemo(memoMap, writer);
			return ResponseEntity.status(HttpStatus.CREATED).body(memoId);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: "+e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
		}
	}

	@Transactional
	@PutMapping("")
	public ResponseEntity<?> receiveMemo(@RequestBody Map<String, String> requestBody, HttpServletRequest request) {
		// TODO: process PUT request
		String linkedToken = requestBody.get("linked_token");
		
	    if (linkedToken == null || linkedToken.trim().isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is missing or empty.");
	    }
		try {
//			String reader = jwtTokenProvider.getUserIdFromLinkedToken(linkedToken);
			String reader = (String) request.getAttribute("userId");
			if (reader == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
			}
			String memoId = jwtTokenProvider.getMemoIdFromLinkedToken(linkedToken);
			int result = memoService.modifyReader(memoId, reader);

			if (result != 1) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Memo not found or cannot be modified.");
			}

			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: "+e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
		}
	}

	@GetMapping("/token/{memoid}")
	public ResponseEntity<?> getLinkedToken(@PathVariable("memoid") String memoId, HttpServletRequest request) {
		try {
		
		String userId = (String) request.getAttribute("userId");
		
		String linkedToken = jwtTokenProvider.createLinkedTokenById(memoId, userId);
		
		return ResponseEntity.ok(linkedToken);
			
		} catch (Exception e) {
			// 예상 못한 예외
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
		}
	}
	
	
}
