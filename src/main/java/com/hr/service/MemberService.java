package com.hr.service;

import com.hr.service.impl.MemberServiceImpl;

public class MemberService implements MemberServiceImpl{

	public boolean checkHaveUser(String username) {
		try {
			//执行持久层的方法,根据用户名查询，如果能查询到结果，就返回true
			//如果不能查询到,就抛出异常,并被catch到返回false
			//这里以后写持久层方法
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	

}
