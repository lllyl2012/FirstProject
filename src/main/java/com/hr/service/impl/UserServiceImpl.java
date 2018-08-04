package com.hr.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hr.mapper.UserMapper;
import com.hr.model.ResponseResult;
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

	@Override
	public void registryCheck(ResponseResult<Void> result, HttpSession session, String token,
			String imageCode, String messageCode, String telephone) {
		// token验证
		String sessionToken = (String) session.getAttribute("token");
		if (sessionToken == null || token == null || !token.equals(sessionToken)) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("CSRF攻击");
		}
		// 验证图片验证码
		String sessionImageCode = (String) session.getAttribute("imageCode");
		if (sessionImageCode == null || imageCode == null || !imageCode.equals(sessionImageCode)) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("验证码不正确");
		}
		// 验证短信验证码
		String sessionMessageCode = (String) session.getAttribute("messageCode");
		if (sessionMessageCode == null || messageCode == null || !messageCode.equals(sessionMessageCode)) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("验证码不正确");
		}
		// 验证用户名是否存在
		boolean ifHaveUsername = checkHaveUser(telephone);
		if (ifHaveUsername) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("该用户名已存在");
		}
	}
}
