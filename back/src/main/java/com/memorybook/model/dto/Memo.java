package com.memorybook.model.dto;

public class Memo {
	private int memoId;
	private String text;
	private int imgNum;
	private String writer;
	private String reader;
	
	public Memo() {
		// TODO Auto-generated constructor stub
	}

	// memoId와 imgNum만 포함하는 생성자
	public Memo(int memoId, int imgNum) {
		this.memoId = memoId;
		this.imgNum = imgNum;
	}

	public Memo(String text, int imgNum, String writer, String reader) {
		this.text = text;
		this.imgNum = imgNum;
		this.writer = writer;
		this.reader = reader;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getImgNum() {
		return imgNum;
	}

	public void setImgNum(int imgNum) {
		this.imgNum = imgNum;
	}

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getMemoId() {
		return memoId;
	}

	public void setMemoId(int memoId) {
		this.memoId = memoId;
	}	
}
