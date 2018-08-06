package com.hr.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hr.model.Company;
import com.hr.model.ResponseResult;
import com.hr.model.User;
import com.hr.utils.CSRFTokenUtil;
import com.hr.utils.SendCodeUtil;
import com.hr.service.BadWordService;
import com.hr.service.CompanyService;
import com.hr.service.ResumeService;
import com.hr.service.UserService;

/**
 * 安全控制中心，登录，注册功能
 * 
 * @author Administrator
 */
@Controller
public class SecurityAction extends CommonAction{
	private final static Logger logger = LoggerFactory.getLogger(SecurityAction.class);
	@Resource
	private ResumeService resumeService;
	@Resource
	private CompanyService companyService;
	@Resource
	private UserService userService;
	@Resource
	private BadWordService badWordService;

	/**
	 * 登录页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {
		logger.debug("登录了");
		return "login";
	}

	/**
	 * 无权限页面
	 * 
	 * @return
	 */
	@RequestMapping("/unauthorized")
	public String unauthorized() {
		return "unauthorized";
	}

	/**
	 * 登出
	 */
	@RequestMapping(value = "/logout", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseResult<Void> logout() {
		ResponseResult<Void> result = new ResponseResult<>();
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			subject.logout();
		}
		result.setStatus(ResponseResult.STATE_OK);
		result.setMessage("登出成功");
		return result;
	}

	/**
	 * 普通用户登录处理
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("/generalLoginHandler")
	@ResponseBody
	public ResponseResult<Map<String, String>> generalLoginHandler(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session) {
		ResponseResult<Map<String, String>> result = new ResponseResult<>();
		Map<String, String> resultMap = new HashMap<>();
		// shiro登录获得用户信息
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			User user = (User) subject.getPrincipal();
			session.setAttribute("user", user);
			// 检查用户简历是否填写完整
			boolean ifFull = resumeService.checkResumeFull();// checkResumeFull()放到userService里面会不会好点
			String resumeFull = ifFull ? "yes" : "no";
			resultMap.put("resumeFull", resumeFull);

			result.setData(resultMap);
			result.setStatus(ResponseResult.STATE_OK);
			result.setMessage("成功登录");
			return result;
		} catch (Exception e) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("用户名或密码错误");
			return result;
		}
	}

	/**
	 * 企业登录
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("/companyLoginHandler")
	@ResponseBody
	public ResponseResult<Map<String, String>> companyLoginHandler(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session) {
		ResponseResult<Map<String, String>> result = new ResponseResult<>();
		Map<String, String> resultMap = new HashMap<>();
		// shiro登录获得用户信息
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			User user = (User) subject.getPrincipal();
			session.setAttribute("user", user);
			// 检查用户简历是否填写完整
			boolean ifFull = companyService.checkResumeFull();
			String resumeFull = ifFull ? "yes" : "no";
			resultMap.put("resumeFull", resumeFull);

			result.setData(resultMap);
			result.setStatus(ResponseResult.STATE_OK);
			result.setMessage("成功登录");
			return result;
		} catch (Exception e) {
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
		String token = CSRFTokenUtil.getTokenForSession(session);
		session.setAttribute("token", token);
		return "generalRegistryPage";
	}

	/**
	 * 返回手机短信验证码
	 * 
	 * @param session
	 * @param telephone
	 * @return
	 */
	@PostMapping("/getSmsCode")
	@ResponseBody
	public ResponseResult<String> getSmsCode(HttpSession session,String imageCode, String telephone) {
		ResponseResult<String> result = new ResponseResult<>();
		// 验证图片验证码
		boolean ifPass = checkImageCode(result, session, imageCode);
		if(!ifPass) {
			return result;
		}
		String smsCode = SendCodeUtil.getSmsCode(telephone);
		session.setAttribute("smsCode", smsCode);
		result.setData(smsCode);
		result.setStatus(ResponseResult.STATE_OK);
		result.setMessage("成功返回");
		return result;
	}

	/**
	 * 生成验证码图片控制器
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getImageCode", produces = "image/png")
	@ResponseBody
	public byte[] getImageCode(HttpSession session) throws IOException {
		String code = SendCodeUtil.getCode(4);
		session.setAttribute("code", code);
		byte[] png = SendCodeUtil.createPng(code);
		return png;
	}

	/**
	 * 处理普通用户注册
	 * 
	 * @param session
	 * @param token
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping("/generalRegistryPageHandler")
	@ResponseBody
	public ResponseResult<Void> generalRegistryPageHandler(HttpSession session, String token, String imageCode,
			String smsCode, String telephone, String password) {
		ResponseResult<Void> result = new ResponseResult<>();
		boolean ifToken = checkToken(result, session, token);
		if(!ifToken) {
			return result;
		}
		boolean ifSmsCode = checkSmsCode(result, session, smsCode);
		if(!ifSmsCode) {
			return result;
		}
		// 检查手机是否注册
		boolean ifHaveTelephone = userService.checkHaveTelephone(telephone);
		if (!ifHaveTelephone) {
			return result;
		}
		Integer insertNum = userService.insertMember(telephone, password);
		if (insertNum != 1) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("注册失败，未知错误");
			return result;
		}
		result.setStatus(ResponseResult.STATE_OK);
		result.setMessage("注册成功");
		return result;
	}

	/**
	 * 处理企业用户注册
	 * 
	 * @param session
	 * @param token
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping("/companyRegistryPageHandler")
	@ResponseBody
	public ResponseResult<Void> companyRegistryPageHandler(HttpSession session, String token, String imageCode,
			String smsCode, Company company) {
		ResponseResult<Void> result = new ResponseResult<>();
		boolean ifToken = checkToken(result, session, token);
		if(!ifToken) {
			return result;
		}
		// 注册前检查公司名和用户名是否被注册
		companyService.registryCheck(result, session, token, company);
		if (result.getStatus() != null) {
			return result;
		}
		// 注册公司
		Integer insertNum = companyService.insertCompany(company);
		if (insertNum != 1) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("注册失败，未知错误");
			return result;
		}
		result.setStatus(ResponseResult.STATE_OK);
		result.setMessage("注册成功");
		return result;
	}

	/**
	 * 检查公司名是否存在
	 * 
	 * @param companyName
	 * @return
	 */
	@PostMapping("/checkCompanyName")
	@ResponseBody
	public ResponseResult<Void> checkCompanyName(String companyName) {
		ResponseResult<Void> result = new ResponseResult<>();
		boolean ifHaveName = companyService.checkCompanyName(companyName);
		if (ifHaveName) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("该公司名已存在");
			return result;
		}
		result.setStatus(ResponseResult.STATE_OK);
		result.setMessage("该公司名已存在");
		return result;
	}

	/**
	 * 密码手机找回页面
	 */
	@GetMapping("/findPwd")
	public String findPwd() {
		return "findPwd";
	}

	/**
	 * 密码手机找回处理
	 */
	@PostMapping("/findPwdHandler")
	@ResponseBody
	public ResponseResult<Void> findPwdHandler(HttpSession session,String smsCode,String telephone) {
		ResponseResult<Void> result = new ResponseResult<>();
		// 先验证验证码是否通过
		boolean ifSmsCode = checkSmsCode(result, session, smsCode);
		if(!ifSmsCode) {
			return result;
		}
		// 再验证号码是否注册过
		boolean ifTelephoneExist = userService.checkHaveTelephone(telephone);
		if(!ifTelephoneExist) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("该号码尚未注册");
			return result;
		}
		//将该号码储存入session
		session.setAttribute("telephone", telephone);
		result.setStatus(ResponseResult.STATE_OK);
		result.setMessage("认证通过");
		return result;
	}
	
	/**
	 * 重置密码页面
	 */
	@GetMapping("/findPasswordChange")
	public String findPasswordChange() {
		return "findPasswordChange";
	}
	
	@PostMapping("/findPasswordChangeHandler")
	@ResponseBody
	public ResponseResult<Void> findPasswordChangeHandler(HttpSession session) {
		ResponseResult<Void> result = new ResponseResult<>();
		String telephone = (String)session.getAttribute("telephone");
		Integer num = userService.updatePassword(telephone);
		if(num == 0) {
			result.setStatus(ResponseResult.STATE_ERROR);
			result.setMessage("重置失败，未知错误");
			return result;
		}
		result.setStatus(ResponseResult.STATE_OK);
		result.setMessage("重置成功");
		return result;
	}
}
