package com.hr.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获得用户或企业的信息
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
public class MemberAction {//刚才查了下百度，有冲突的话
	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		String hello = "hello world";
		return hello;
		
	}
	
	
}
