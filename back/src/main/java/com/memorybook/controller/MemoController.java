package com.memorybook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memorybook.model.dto.Memo;
import com.memorybook.model.service.MemoService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/memo")
public class MemoController {
	private final MemoService memoService;

	public MemoController(MemoService memoService) {
		// TODO Auto-generated constructor stub
		this.memoService = memoService;
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
			List<Memo> memos = memoService.getMemosByReader(reader);
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
	@PostMapping("")
	public ResponseEntity<?> sendMemo(@RequestBody Map<String, String> memoMap, HttpServletRequest request) {
		// TODO: process POST request
		try {
			String writer = (String) request.getAttribute("userId");
			if (writer == null) {
				System.out.println("Unauthorized access attempt to write memo");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
			}

			int memoId = memoService.writeMemo(memoMap, writer);
			return ResponseEntity.status(HttpStatus.CREATED).body(memoId);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public String putMethodName(@PathVariable String id, @RequestBody String entity) {
		// TODO: process PUT request

		return entity;
	}

}
