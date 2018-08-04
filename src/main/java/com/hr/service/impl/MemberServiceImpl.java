package com.hr.service.impl;

import org.springframework.stereotype.Service;

import com.hr.service.MemberService;
@Service("memberService")
public class MemberServiceImpl implements MemberService{
	/**
	 * 检查用户名是否存在
	 */
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
