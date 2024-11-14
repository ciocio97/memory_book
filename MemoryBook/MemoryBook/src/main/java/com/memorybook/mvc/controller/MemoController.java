package com.memorybook.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memorybook.mvc.model.dto.Memo;
import com.memorybook.mvc.model.service.MemoService;

import java.util.List;
import java.util.Map;

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
	public ResponseEntity<?> getMemos(@RequestHeader("userId") String reader) {
		
		List<Memo> memos = memoService.getMemosByReader(reader);
		if(memos == null) {
			//받은 메모가 없음 -> 어떻게 처리할지??
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		//받은 메모들을 보내줌.
		return new ResponseEntity<>(memos, HttpStatus.OK);
	}
	
	@Transactional
	@PostMapping("")
	public ResponseEntity<?> sendMemo(@RequestBody Map<String, String> memoMap, @RequestHeader("userId") String writer) {
		//TODO: process POST request
		int memoId = memoService.writeMemo(memoMap, writer);
		if(memoId == 0) {
			// 작성 실패
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(memoId,HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public String putMethodName(@PathVariable String id, @RequestBody String entity) {
		//TODO: process PUT request
		
		return entity;
	}
	
}
