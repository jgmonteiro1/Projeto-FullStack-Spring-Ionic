package com.jgmonteiro.projetofullstack.service;

import org.springframework.mail.SimpleMailMessage;

import com.jgmonteiro.projetofullstack.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
}
