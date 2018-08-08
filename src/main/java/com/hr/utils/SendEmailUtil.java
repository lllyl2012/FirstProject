package com.hr.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;

@Component("sendEmailUtil")
public class SendEmailUtil {
	@Autowired
	private static JavaMailSender jms;// 不知道自动注入静态变量可不可以，假如运行有出错这里要注意一下

	public SendEmailUtil(JavaMailSender jms) {
		this.jms = jms;
	}

	public static String sendEmail() {
		// 建立邮件消息
		SimpleMailMessage mainMessage = new SimpleMailMessage();
		// 发送者
		mainMessage.setFrom("qibuqimingzi@126.com");
		// 接收者
		mainMessage.setTo("229707363@qq.com");
		// 发送的标题
		mainMessage.setSubject("xxxxx");
		// 发送的内容
		mainMessage.setText("hello world");
		jms.send(mainMessage);
		return "send ok";
	}

	public static String sendAttachMail(StringBuilder sb) {
		MimeMessage message = null;
		try {
			message = jms.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			// 发送者
			helper.setFrom("qibuqimingzi@126.com");
			// 接收者
			helper.setTo("229707363@qq.com");
			// 发送的标题
			helper.setSubject("xxxxx");

			helper.setText(sb.toString(), true);
			FileSystemResource fileSystemResource = new FileSystemResource(new File("E:\\aaa.docx"));
			helper.addAttachment("aa.docx", fileSystemResource);
			jms.send(message);
			return "attach";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "error";
		}
	}

	public JavaMailSender getJms() {
		return jms;
	}

	public void setJms(JavaMailSender jms) {
		SendEmailUtil.jms = jms;
	}
}
