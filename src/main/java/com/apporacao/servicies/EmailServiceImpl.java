package com.apporacao.servicies;

import java.net.SocketTimeoutException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.apporacao.dtos.ConviteDTO;

public class EmailServiceImpl {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private TemplateEngine template;
	
	
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
	
	public void sendEmailConvite(ConviteDTO dto, String subject, String conviteCrypt, String nameRemetente, String nameDestino ) throws SocketTimeoutException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg);
		
		try {
			helper.setTo(dto.getEmailReceiver());
			helper.setFrom(dto.getEmailSender());
			helper.setSubject(subject);
			helper.setSentDate(new Date(System.currentTimeMillis()));
			helper.setText(htmlEmailConvite(nameRemetente, nameDestino, conviteCrypt), true);
			
			javaMailSender.send(msg);
			LOG.info("Email enviado para: "+dto.getEmailReceiver());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void sendEmailNewPassword(String emailSender, String emailReceiver, String subject, String codigo) throws SocketTimeoutException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg);
		
		try {
			helper.setTo(emailReceiver);
			helper.setFrom(emailSender);
			helper.setSubject(subject);
			helper.setSentDate(new Date(System.currentTimeMillis()));
			helper.setText(htmlEmailCodigo(codigo), true);
			
			javaMailSender.send(msg);
			LOG.info("Email enviado para: "+emailReceiver);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private String htmlEmailConvite(String nameRemetente, String nameDestino, String conviteCrypt) {
		Context context = new Context();
		context.setVariable("remetente", nameRemetente);
		context.setVariable("destino", nameDestino);
		context.setVariable("convite", conviteCrypt);
		return template.process("email/convite", context);
	}
	
	private String htmlEmailCodigo(String codigo) {
		Context context = new Context();
		context.setVariable("codigo", codigo);
		return template.process("email/newPassword", context);
	}
	
}
