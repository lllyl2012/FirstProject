package com.hr.service;

import com.hr.model.User;

public interface UserService {
	/**
	 * 检查用户名是否存在
	 * @param username
	 * @return
	 */
	boolean checkHaveUser(String username);

	Integer insertMember(String username, String password);
	
	User findByUsername(String username);
}
