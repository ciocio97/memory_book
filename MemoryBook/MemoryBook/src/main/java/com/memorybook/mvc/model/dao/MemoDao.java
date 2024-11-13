package com.memorybook.mvc.model.dao;

import java.util.List;

import com.memorybook.mvc.model.dto.Memo;

public interface MemoDao {

	List<Memo> selectByReader(String reader);

}
