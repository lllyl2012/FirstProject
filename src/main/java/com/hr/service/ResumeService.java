package com.hr.service;

import org.springframework.stereotype.Service;

import com.hr.service.impl.ResumeServiceImpl;

@Service
public class ResumeService implements ResumeServiceImpl{
	/**
	 * 检查用户简历是否完整
	 */
	public boolean checkResumeFull() {
		try {
			//执行持久层的方法,如果数据所有库字段都非空,就返回true
			//如果有字段是空,就抛出异常,并被catch到返回false
			//这里以后写持久层方法
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}

}
