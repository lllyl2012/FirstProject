package com.hr.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hr.model.User;
import com.hr.service.UserService;
import com.hr.utils.SendEmailUtil;

@Controller
public class TopUrlAction {
	
	@RequestMapping("/index")
	public String index(Model model,HttpSession session) {
		System.out.println(session.getAttribute("name"));
		System.out.println(session.getId());
		return "index";
	}
	
	@RequestMapping("/emailDemo")
	@ResponseBody
	public String emailDemo() {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href='http://www.baidu.com'>qqqqqq</a>");
		SendEmailUtil.sendAttachMail(sb);
		return "a";
	}
}
