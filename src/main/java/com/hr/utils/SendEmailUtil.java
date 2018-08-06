package com.hr.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import org.springframework.beans.factory.annotation.Autowired;

public class SendEmailUtil {
	
	@Autowired
	private JavaMailSender jms;
	public String sendEmail(){
		//建立邮件消息
		SimpleMailMessage mainMessage = new SimpleMailMessage();
		//发送者
		mainMessage.setFrom("xxxxx@163.com");
		//接收者
		mainMessage.setTo("xxxx@qq.com");
		//发送的标题
		mainMessage.setSubject("嗨喽");
		//发送的内容
		mainMessage.setText("hello world");
		jms.send(mainMessage);
		return "send ok";
	}

}
