package com.hr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopUrlAction {

	@RequestMapping("/index")
	public String index() {
		System.out.println(22);
		return "index";
	}
}
