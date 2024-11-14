package com.memorybook.mvc.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.memorybook.mvc.model.dao.MemoDao;
import com.memorybook.mvc.model.dto.Memo;

@Service
public class MemoServiceImpl implements MemoService {
	private final MemoDao memoDao;

	public MemoServiceImpl(MemoDao memoDao) {
		// TODO Auto-generated constructor stub
		this.memoDao = memoDao;
	}

	@Override
	public List<Memo> getMemosByReader(String reader) {
		// TODO Auto-generated method stub
		return memoDao.selectByReader(reader);
	}

	@Override
	public int writeMemo(Map<String, String> memoMap, String writer) {
		// TODO Auto-generated method stub
		Memo memo = new Memo();
		memo.setWriter(writer);
		memo.setImgNum(Integer.parseInt(memoMap.get("imgNum")));
		memo.setReader(memoMap.get("reader"));
		memo.setText(memoMap.get("text"));

		int result = memoDao.insert(memo);

		if (result == 0) {
			return 0;
		}
		return memo.getMemoId();
	}
}
