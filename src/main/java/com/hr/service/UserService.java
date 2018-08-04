package com.hr.service;

import javax.servlet.http.HttpSession;

import com.hr.model.ResponseResult;
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
	 * 注册用户前检查
	 * @param result
	 * @param session
	 * @param token
	 * @param imageCode
	 * @param messageCode
	 * @param username
	 * @return
	 */
	void registryCheck(ResponseResult<Void> result,HttpSession session,String token,String imageCode,String messageCode,String telephone);
}
