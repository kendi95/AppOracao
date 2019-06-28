package com.apporacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apporacao.servicies.EmailServiceImpl;

@Configuration
public class MailConfig {
	
	@Bean
	public EmailServiceImpl emailServiceImpl() {
		return new EmailServiceImpl();
	}

}
