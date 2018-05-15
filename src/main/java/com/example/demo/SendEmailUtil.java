package com.example.demo;

import java.io.File;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * Spring Mail
 * API都在org.springframework.mail及其子包org.springframework.mail.javamail中封装，
 * 且只提供了邮件发送的封装。 SimpleMailMessage: 对邮件的一个简单封装，只能用于表示一个纯文本的邮件，也不能包含附件等。
 * JavaMailSenderImpl: 邮件发送器，主要提供了邮件发送接口、透明创建Java
 * Mail的MimeMessage、及邮件发送的配置(如:host/port/username/password...)。
 * MimeMailMessage、MimeMessageHelper：对MimeMessage进行了封装。
 * Spring还提供了一个回调接口MimeMessagePreparator, 用于准备JavaMail的MIME信件
 * 一下代码转载自:http://www.blogjava.net/tangzurui/archive/2008/12/08/244953.html
 * 
 * @description: 使用spring-integration-mail完成邮箱发送
 * @author: skyler
 * @time: 2016年7月5日 下午6:18:05
 */
public class SendEmailUtil {

	public static void main(String[] args) throws Exception {

		JavaMailSender sender = initJavaMailSender();
		// sendText(sender);
		// sendHtml(sender);
		// sendTextWithImg(sender);
		sendWithAttament(sender);
	}

	/**
	 * 
	 * @description: 实例化JavaMailSender。
	 * @author: skyler
	 * @time: 2016年7月7日 下午4:56:53
	 */
	public static JavaMailSender initJavaMailSender() {
		/*
		 * //从网上找的参考，说要添加这么多，实际测试了下，不需要这么多个 Properties properties = new
		 * Properties(); properties.setProperty("mail.debug", "true");
		 * properties.setProperty("mail.smtp.socketFactory.class",
		 * "javax.net.ssl.SSLSocketFactory");
		 * properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		 * properties.setProperty("mail.smtp.socketFactory.port", "465");
		 * properties.setProperty("mail.smtp.port", "465");
		 * properties.setProperty("mail.smtp.auth", "true");
		 */
		Properties properties = new Properties();
		properties.setProperty("mail.debug", "true");// 是否显示调试信息(可选)
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.smtp.auth", "true");
		properties.put(" mail.smtp.timeout ", " 25000 ");

		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setJavaMailProperties(properties);
		javaMailSender.setHost("smtp.163.com");
		javaMailSender.setUsername("abc"); // 根据自己的情况,设置username
		javaMailSender.setPassword("abc"); // 根据自己的情况, 设置password
		javaMailSender.setPort(465);
		javaMailSender.setDefaultEncoding("UTF-8");

		return javaMailSender;
	}

	/**
	 * @description: 发送text
	 * @throws Exception
	 * 
	 * @author: skyler
	 * @since: since from which version support
	 * @time: 2016年7月5日 下午7:46:11
	 */
	public static void sendText(JavaMailSender sender) {
		// 建立邮件消息
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		// 设置收件人，寄件人 用数组发送多个邮件
		// String[] array = new String[] {"sun111@163.com","sun222@sohu.com"};
		// mailMessage.setTo(array);
		mailMessage.setTo("111@163.com");
		// 可选的，可以用来修改显示给接收者的名字
		mailMessage.setFrom("abc@163.com");
		mailMessage.setSubject(" 测试简单文本邮件发送！ ");
		mailMessage.setText(" 测试我的简单邮件发送机制！！ ");

		// 发送邮件
		sender.send(mailMessage);

		System.out.println(" 邮件发送成功.. ");
	}

	/**
	 * @description: 发送html,org.springframework.mail.javamail.
	 *               MimeMessageHelper是处理JavaMail邮件常用的顺手组件之一。它可以让你摆脱繁复的javax.
	 *               mail.internetAPI类
	 * @throws Exception
	 * 
	 * @author: skyler
	 * @since: since from which version support
	 * @time: 2016年7月5日 下午7:46:11
	 */
	public static void sendHtml(JavaMailSender sender) throws Exception {
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = sender.createMimeMessage();
		// MimeMessageHelper messageHelper = new
		// MimeMessageHelper(mailMessage);这个构造函数会出现中文乱码的问题
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "GBK");

		// 设置收件人，寄件人
		messageHelper.setTo("111@163.com");
		messageHelper.setFrom("abc@163.com");
		messageHelper.setSubject("测试HTML邮件！");
		// true 表示启动HTML格式的邮件
		String name = "欢迎";
		messageHelper.setText(
				name + "：<a href='http://10.125.72.40/admin/login.html'>http://10.125.72.40/admin/login.html</a>",
				true);

		// 发送邮件
		sender.send(mailMessage);

		System.out.println("邮件发送成功..");
	}

	/**
	 * @description: 发送嵌套图片的邮件.Email允许添加附件，也允许在multipart信件中内嵌资源。
	 *               内嵌资源可能是你在信件中希望使用的图像，或者样式表，但是又不想把它们作为附件。 说明：嵌入图片<img
	 *               src=\"cid:aaa\"/>，其中cid:是固定的写法，而aaa是一个contentId
	 * @throws Exception
	 * 
	 * @author: skyler
	 * @since: since from which version support
	 * @time: 2016年7月5日 下午7:46:11
	 */
	public static void sendTextWithImg(JavaMailSender sender) throws MessagingException {
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = sender.createMimeMessage();
		// 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
		// multipart模式,同时解决中文乱码问题
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "GBK");

		// 设置收件人，寄件人
		messageHelper.setTo("111@163.com");
		messageHelper.setFrom("abc@163.com");
		messageHelper.setSubject("测试邮件中嵌套图片!！");
		// true 表示启动HTML格式的邮件
		messageHelper.setText("<html><head></head><body><h1>hello 欢迎你!!spring image html mail</h1>"
				+ "<img src=\"cid:aaa\"/></body></html>", true);

		FileSystemResource img = new FileSystemResource(new File("d:/Desert.jpg"));

		messageHelper.addInline("aaa", img);

		// 发送邮件
		sender.send(mailMessage);

		System.out.println("邮件发送成功..");
	}

	/**
	 * @description: 本类测试的是关于邮件中带有附件的例子 .发送包含附件的邮件
	 * @throws Exception
	 * 
	 * @author: skyler
	 * @throws MessagingException
	 * @since: since from which version support
	 * @time: 2016年7月5日 下午7:46:11
	 */
	public static void sendWithAttament(JavaMailSender sender) throws MessagingException {
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = sender.createMimeMessage();
		// 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
		// multipart模式 为true时发送附件 可以设置html格式
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");

		// 设置收件人，寄件人
		messageHelper.setTo("111@163.com");
		messageHelper.setFrom("abc@163.com");
		messageHelper.setSubject("测试邮件中上传附件!！");
		// true 表示启动HTML格式的邮件
		messageHelper.setText("<html><head></head><body><h1>你好：附件中有学习资料！</h1></body></html>", true);

		FileSystemResource file = new FileSystemResource(new File("d:/logback.xml"));
		// 这里的方法调用和插入图片是不同的。
		messageHelper.addAttachment("logback.xml", file);

		// 发送邮件
		sender.send(mailMessage);

		System.out.println("邮件发送成功..");

	}
}