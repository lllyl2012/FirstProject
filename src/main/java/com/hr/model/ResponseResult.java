package com.hr.model;

import java.io.Serializable;

/**
 * 返回的json数据，全部封装在这里
 * @author Administrator
 *
 * @param <T>
 */
public class ResponseResult<T> implements Serializable{
	private static final long serialVersionUID = 341424198528175317L;
	
	public static final int STATE_OK = 1;
	//出现错误
	public static final int STATE_ERROR = -1;
	//未登陆
	public static final int NO_LOGIN = 0;
	private T data;
	private String message;
	private Integer status;
	
	public ResponseResult() {
		super();
	}

	public ResponseResult(T data, String message, Integer status) {
		super();
		this.data = data;
		this.message = message;
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseResult other = (ResponseResult) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponseResult [data=" + data + ", message=" + message
				+ ", status=" + status + "]";
	}
	
}
