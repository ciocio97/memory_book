package com.memorybook.mvc.model.service;

import java.util.List;
import java.util.Map;

import com.memorybook.mvc.model.dto.Memo;

public interface MemoService {

	List<Memo> getMemosByReader(String reader);

	int writeMemo(Map<String, String> memoMap, String writer);
}
