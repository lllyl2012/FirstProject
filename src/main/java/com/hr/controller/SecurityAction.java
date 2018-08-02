package com.hr.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;
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
	 * 普通用户登录处理
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("/generalLoginHandler")
	@ResponseBody
	public ResponseResult generalLoginHandler(@RequestParam("username") String username,@RequestParam("password") String password,HttpSession session) {
		ResponseResult<Map<String,String>> result = new ResponseResult<>();
		Map<String,String> resultMap = new HashMap<>();
		//shiro登录获得用户信息
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			User user = (User) subject.getPrincipal();
			session.setAttribute("user",user);
			//检查用户简历是否填写完整
			boolean ifFull = resumeService.checkResumeFull();
			String resumeFull = ifFull?"yes":"no";
			resultMap.put("resumeFull", resumeFull);
			
			result.setData(resultMap);
			result.setStatus(ResponseResult.STATE_OK);
			result.setMessage("成功登录");
			return result;
		}catch(Exception e) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("用户名或密码错误");
			return result;
		}
	}
	
	/**
	 * 企业登录
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("/companyLoginHandler")
	@ResponseBody
	public ResponseResult companyLoginHandler(@RequestParam("username") String username,@RequestParam("password") String password,HttpSession session) {
		ResponseResult<Map<String,String>> result = new ResponseResult<>();
		Map<String,String> resultMap = new HashMap<>();
		//shiro登录获得用户信息
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			User user = (User) subject.getPrincipal();
			session.setAttribute("user",user);
			//检查用户简历是否填写完整
			boolean ifFull = companyInfoService.checkResumeFull();
			String resumeFull = ifFull?"yes":"no";
			resultMap.put("resumeFull", resumeFull);
			
			result.setData(resultMap);
			result.setStatus(ResponseResult.STATE_OK);
			result.setMessage("成功登录");
			return result;
		}catch(Exception e) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("用户名或密码错误");
			return result;
		}
	}
	
	/**
	 * 注册页面
	 */
	@GetMapping("/generalRegistryPage")
	public String generalRegistryPage(HttpSession session) {
		String token = CSRFtokenUtil.getToken();
		session.setAttribute("token", "363636");
		return "generalRegistryPage";
	}

	/**
	 * 返回手机短信验证码
	 * @param session
	 * @param telephone
	 * @return
	 */
	@PostMapping("/GetMessageCode")
	@ResponseBody
	public ResponseResult<String> getMessageCode(HttpSession session, String telephone){
		ResponseResult<String> result = new ResponseResult<>();
		String messageCode = SendCodeUtil.getMessageCode(telephone);
		session.setAttribute("messageCode", messageCode);
		result.setData(messageCode);
		result.setStatus(ResponseResult.STATE_OK);
		result.setMessage("成功返回");
		return result;
	}
	
	/**
	 * 返回图片验证码
	 * @param session
	 * @param telephone
	 * @return
	 */
	@PostMapping("/GetMessageCode")
	@ResponseBody
	public ResponseResult<String> getMessageCode(HttpSession session, String telephone){
		ResponseResult<String> result = new ResponseResult<>();
		String imageCode = SendCodeUtil.getImageCode(telephone);
		session.setAttribute("imageCode", imageCode);
		result.setData(imageCode);
		result.setStatus(ResponseResult.STATE_OK);
		result.setMessage("成功返回");
		return result;
	}
	
	/**
	 * 处理普通用户注册
	 * @param session
	 * @param token
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping("/generalRegistryPageHandler")
	@ResponseBody
	public ResponseResult<Void> generalRegistryPageHandler(HttpSession session,String token,String imageCode,String messageCode,String username, String password) {
		ResponseResult<Void> result = new ResponseResult<>();
		//token验证
		String sessionToken = (String)session.getAttribute("token");
		if(sessionToken == null || token == null || !token.equals(sessionToken)) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("CSRF攻击");
			return result;
		}
		//验证图片验证码
		String sessionImageCode = (String)session.getAttribute("imageCode");
		if(sessionImageCode == null || imageCode == null || !imageCode.equals(sessionImageCode)) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("验证码不正确");
			return result;
		}
		//验证短信验证码
		String sessionMessageCode = (String)session.getAttribute("messageCode");
		if(sessionMessageCode == null || messageCode == null || !messageCode.equals(sessionMessageCode)) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("验证码不正确");
			return result;
		}
		//验证用户名是否存在
		boolean ifHaveUsername = memberService.checkHaveUser(username);
		if(ifHaveUsername) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("该用户名已存在");
			return result;
		}
	}
}
