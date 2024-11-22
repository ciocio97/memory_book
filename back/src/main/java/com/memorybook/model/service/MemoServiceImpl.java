package com.memorybook.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.memorybook.model.dao.MemoDao;
import com.memorybook.model.dto.Memo;

@Service
public class MemoServiceImpl implements MemoService {
	private final MemoDao memoDao;

	public MemoServiceImpl(MemoDao memoDao) {
		// TODO Auto-generated constructor stub
		this.memoDao = memoDao;
	}

	@Override
	public List<Map<String, Object>> getMemosByReader(String reader) {
		try {
			List<Memo> originalMemos = memoDao.selectByReader(reader);
			List<Map<String, Object>> filteredMemos = originalMemos.stream().map(memo -> {
				Map<String, Object> mempMap = new HashMap<>();
				mempMap.put("memoId", memo.getMemoId());
				mempMap.put("ingNum", memo.getImgNum());
				return mempMap;
			}).collect(Collectors.toList());
			return filteredMemos;
		} catch (DataAccessException e) {
			throw new RuntimeException("Database error occurred while getting memos");
		}
	}

	@Override
	public Memo getMemoById(String memoId) {
		try {
			int id = Integer.parseInt(memoId.trim());

			return memoDao.selectMemoById(id);
		} catch (DataAccessException e) {
			throw new RuntimeException("Database error occurred while getting memo");
		}
	}

	@Override
	public int writeMemo(Map<String, String> memoMap, String writer) {
		try {
			// 데이터 유효성 검증
			if (memoMap == null || memoMap.get("imgNum") == null || memoMap.get("text") == null) {
				// reader can null
				throw new IllegalArgumentException("Memo data is incomplete. Required fields are missing.");
			}

			// memo객체 생성
			Memo memo = new Memo();
			memo.setWriter(writer);
			try {
				memo.setImgNum(Integer.parseInt(memoMap.get("imgNum")));

			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid imgNum. It must be a valid integer.", e);
			}
			memo.setReader(memoMap.get("reader"));
			memo.setText(memoMap.get("text"));

			int result = memoDao.insert(memo);

			if (result != 1) {
				throw new RuntimeException("Failed to insert memo into the database");
			}
			return memo.getMemoId();

		} catch (DataAccessException e) {
			// 데이터베이스 관련 예외 처리
			throw new RuntimeException("Database error occurred while writing memo.", e);
		} catch (IllegalArgumentException e) {
			// 잘못된 입력 데이터 처리
			throw e;
		} catch (Exception e) {
			// 기타 예상하지 못한 예외 처리
			throw new RuntimeException("An unexpected error occurred while writing memo.", e);
		}
	}

	@Override
	public int modifyReader(String memoId, String reader) {
		System.out.println(memoId);
		int id = Integer.parseInt(memoId.trim());
		System.out.println(id);
		try {
			Memo memo = memoDao.selectMemoById(id);
			if (memo.getReader() != null) {
				System.out.println(memo.getReader());
				throw new IllegalArgumentException("A reader has already been assigned. You cannot set a new reader.");
			}
			System.out.println(memo.toString());
			// reader가 null이면 갱신

			memo.setReader(reader); // 새로운 리더로 set

			return memoDao.updateReader(memo);
		} catch (DataAccessException e) {
			// 데이터베이스 관련 예외 처리
			throw new RuntimeException("Database error occurred while modifing memo.", e);
		} catch (Exception e) {
			// 기타 예상하지 못한 예외 처리
			throw new RuntimeException("An unexpected error occurred while modifing memo.", e);
		}
	}

}
