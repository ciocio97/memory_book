package com.memorybook.model.service;

import java.util.List;
import java.util.Map;

import com.memorybook.model.dto.Memo;

public interface MemoService {

	List<Memo> getMemosByReader(String reader);

	int writeMemo(Map<String, String> memoMap, String writer);

	int modifyReader(String memoId, String reader);
}
