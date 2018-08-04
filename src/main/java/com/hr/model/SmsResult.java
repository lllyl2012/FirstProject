package com.hr.model;

/**
 * 储存短信返回信息实体类
 * 
 * @author volume
 */
public class SmsResult {
	private String status;
	private String message;

	public SmsResult() {
		super();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SmsResult(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

}