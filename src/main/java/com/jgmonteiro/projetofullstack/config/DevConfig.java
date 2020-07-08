package com.jgmonteiro.projetofullstack.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jgmonteiro.projetofullstack.service.DBService;
import com.jgmonteiro.projetofullstack.service.EmailService;
import com.jgmonteiro.projetofullstack.service.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Override
	public void run(String... args) throws Exception {
				
		

	}
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		/*
		 if!strategy.contentEquals("create"){
		 } 
		 
		 */
		if(!"create".equals(strategy)) {
			return false;
		}
		dbService.instantiateTestDataBase();
		return true;
	}
	
	//Instancia um SmtpEmailService sempre que eu rodar meu programa no perfil dev 
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
