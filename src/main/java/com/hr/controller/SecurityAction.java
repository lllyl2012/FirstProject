package com.hr.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hr.model.ResponseResult;
import com.hr.model.User;

/**
 * 安全控制中心，登录，注册功能
 * 
 * @author Administrator
 */
@Controller
public class SecurityAction {
	/**
	 * 登录页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	/**
	 * 无权限页面
	 * @return
	 */
	@RequestMapping("/unauthorized")
	public String unauthorized() {
		return "unauthorized";
	}
	
	/**
	 * 登出
	 */
	@RequestMapping(value="/logout",produces="application/json;charset=UTF-8")
	@ResponseBody
	public ResponseResult<Void> logout() {
		ResponseResult<Void> result = new ResponseResult<>();
		Subject subject = SecurityUtils.getSubject();
		if(subject != null) {
			subject.logout();
		}
		result.setStatus(ResponseResult.STATE_OK);
		result.setMessage("登出成功");
		return result;
	}
	
	/**
	 * 登录处理
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("/loginHandler")
	public String loginHandler(@RequestParam("username") String username,@RequestParam("password") String password,HttpSession session) {
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			User user = (User) subject.getPrincipal();
			session.setAttribute("user",user);
			return "index";
		}catch(Exception e) {
			return "login";
		}
	}
}
