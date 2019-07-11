package com.apporacao.servicies;

import java.net.SocketTimeoutException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailServiceImpl {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	MailSender mailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	public void sendSimpleMessage(String from, String to, String subject, String message) throws MailException {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setFrom(from);
		msg.setSubject(subject);
		msg.setText(message);
		
		javaMailSender = new JavaMailSenderImpl();
		javaMailSender.send(msg);
		LOG.info("Email enviado para: "+msg.getTo());
	}
	
	public void sendCustomMessage(String from, String to, String subject, String message) throws SocketTimeoutException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg);
		
		try {
			helper.setTo(to);
			helper.setFrom(from);
			helper.setText(message);
			helper.setSubject(subject);
			
			javaMailSender.send(msg);
			LOG.info("Email enviado para: "+to);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
