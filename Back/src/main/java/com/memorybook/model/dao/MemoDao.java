package com.memorybook.model.dao;

import java.util.List;

import com.memorybook.model.dto.Memo;

public interface MemoDao {

	List<Memo> selectByReader(String reader);

	int insert(Memo memo);

}
