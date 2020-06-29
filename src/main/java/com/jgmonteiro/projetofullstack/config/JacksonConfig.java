//Classe para configurar o Jackson
package com.jgmonteiro.projetofullstack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgmonteiro.projetofullstack.domain.PagamentoComBoleto;
import com.jgmonteiro.projetofullstack.domain.PagamentoComCartao;

@Configuration
public class JacksonConfig {
	// Método para registrar as subclasses que serão mapeadas pelo JsonType
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PagamentoComCartao.class);
				objectMapper.registerSubtypes(PagamentoComBoleto.class);
				super.configure(objectMapper);
			};
		};
		return builder;
	}
}
