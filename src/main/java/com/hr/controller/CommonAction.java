package com.hr.controller;

import javax.servlet.http.HttpSession;

import com.hr.model.ResponseResult;

public abstract class CommonAction {
	protected boolean checkImageCode(ResponseResult result, HttpSession session, String imageCode) {
		// 验证图片验证码
		String sessionImageCode = (String) session.getAttribute("imageCode");
		if (sessionImageCode == null || imageCode == null || !imageCode.equals(sessionImageCode)) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("验证码不正确");
			return false;
		}
		return true;
	}

	protected boolean checkToken(ResponseResult result, HttpSession session, String token) {
		// token验证
		String sessionToken = (String) session.getAttribute("token");
		if (sessionToken == null || token == null || !token.equals(sessionToken)) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("CSRF攻击");
			return false;
		}
		return true;
	}

	protected boolean checkSmsCode(ResponseResult result, HttpSession session, String smsCode) {
		// 验证短信验证码
		String sessionsmsCode = (String) session.getAttribute("smsCode");
		if (sessionsmsCode == null || smsCode == null || !smsCode.equals(sessionsmsCode)) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("验证码不正确");
			return false;
		}
		return true;
	}
}
