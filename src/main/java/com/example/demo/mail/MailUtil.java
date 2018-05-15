package com.example.demo.mail;

import java.util.Properties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailUtil {

	// 使用对象注入的方式 记得配置文件
	private JavaMailSenderImpl senderImpl;

	private SimpleMailMessage mailMessage;

	private Properties prop;

	public MailUtil() {
		this.senderImpl = new JavaMailSenderImpl();
		this.mailMessage = new SimpleMailMessage();
		this.prop = new Properties();
	}

	/**
	 * 发送邮件
	 * 
	 * @param to
	 *            目标邮箱地址
	 * @param text
	 *            邮件内容
	 * @return
	 */
	public boolean sendMail(String text, String... to) {
		System.out.println("sendMail...util...");

		try {

			mailMessage.setTo(to);
			mailMessage.setFrom("15291773347@163.com");
			mailMessage.setSubject("主题");
			mailMessage.setText(text);

			// 设定mail server
			senderImpl.setHost("smtp.163.com");
			senderImpl.setUsername("15291773347@163.com");
			senderImpl.setPassword("915915");

			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.timeout", "25000");
			senderImpl.setJavaMailProperties(prop);

			// 发送邮件
			senderImpl.send(mailMessage);

			System.out.println("发送邮件成功");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发送邮件失败");
			return false;
		}
	}
}
