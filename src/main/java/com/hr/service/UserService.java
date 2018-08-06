package com.hr.service;

import com.hr.model.User;

public interface UserService {
	/**
	 * 检查用户名是否存在
	 * @param username
	 * @return
	 */
	boolean checkHaveUser(String username);

	/**
	 * 注册用户
	 * @param username
	 * @param password
	 * @return
	 */
	Integer insertMember(String username, String password);
	
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	User findByUsername(String username);
	
	/**
	 * 检查是否存在该手机号码
	 * @param telephone
	 * @return
	 */
	boolean checkHaveTelephone(String telephone);

	/**
	 * 重置密码
	 * @param telephone
	 * @return
	 */
	Integer updatePassword(String telephone);
}
