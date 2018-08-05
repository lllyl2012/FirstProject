package com.hr.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hr.mapper.UserMapper;
import com.hr.model.User;
import com.hr.service.UserService;

@Service("memberService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	/**
	 * 检查用户名是否存在
	 */
	public boolean checkHaveUser(String username) {
		try {
			// 执行持久层的方法,根据用户名查询，如果能查询到结果，就返回true
			// 如果不能查询到,就抛出异常,并被catch到返回false
			// 这里以后写持久层方法
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 插入用户
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public Integer insertMember(String username, String password) {
		return 1;
	}

	/**
	 * 根据用户名查询用户
	 */
	@Override
	public User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}

	/**
	 * 检查是否存在该手机号码
	 */
	@Override
	public boolean checkHaveTelephone(String telephone) {
		return true;
	}

	/**
	 * 重置密码
	 */
	@Override
	public Integer updatePassword(String telephone) {
		// TODO Auto-generated method stub
		return null;
	}
}
