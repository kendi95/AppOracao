package com.apporacao.dtos;

import java.io.Serializable;

public class ConviteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String emailSender;
	private String emailReceiver;
	private String tipo;
	
	
	public ConviteDTO() {}

	public ConviteDTO(String emailSender, String emailReceiver, String tipo) {
		super();
		this.emailSender = emailSender;
		this.emailReceiver = emailReceiver;
		this.tipo = tipo;
	}

	
	
	public String getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(String emailSender) {
		this.emailSender = emailSender;
	}

	public String getEmailReceiver() {
		return emailReceiver;
	}

	public void setEmailReceiver(String emailReceiver) {
		this.emailReceiver = emailReceiver;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	

}
