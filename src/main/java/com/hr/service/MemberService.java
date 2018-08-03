package com.hr.service;


public interface MemberService {
	/**
	 * 检查用户名是否存在
	 * @param username
	 * @return
	 */
	public boolean checkHaveUser(String username);
}
