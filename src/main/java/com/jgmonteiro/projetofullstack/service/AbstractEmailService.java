package com.jgmonteiro.projetofullstack.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.jgmonteiro.projetofullstack.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		//Para quem será enviado a mensagem
		sm.setTo(obj.getCliente().getEmail());
		//Quem será o remetente do email(no caso será o email padrão da aplicação)
		sm.setFrom(sender);
		//Assunto do e-mail
		sm.setSubject("Pedido confirmado! Código: "+obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		//Corpo do email
		sm.setText(obj.toString());
		return sm;
	}
}
