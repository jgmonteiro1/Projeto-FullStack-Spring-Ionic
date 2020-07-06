package com.jgmonteiro.projetofullstack.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jgmonteiro.projetofullstack.service.DBService;
import com.jgmonteiro.projetofullstack.service.EmailService;
import com.jgmonteiro.projetofullstack.service.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private DBService dbService;
	
	@Override
	public void run(String... args) throws Exception {
				
		

	}
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDataBase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}
