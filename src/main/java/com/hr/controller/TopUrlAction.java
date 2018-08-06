package com.hr.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hr.model.User;
import com.hr.service.UserService;

@Controller
public class TopUrlAction {
	@RequestMapping("/index")
	public String index(Model model) {
		return "index";
	}
}
