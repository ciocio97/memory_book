package com.memorybook.mvc.model.dto;

public class Memo {
	private String text;
	private int imgNum;
	private String writer;
	private String reader;
	
	public Memo() {
		// TODO Auto-generated constructor stub
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
}
