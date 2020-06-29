package com.jgmonteiro.projetofullstack.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.jgmonteiro.projetofullstack.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pag, Date instante) {
		Calendar cal  = Calendar.getInstance();
		cal.setTime(instante);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pag.setDataVencimento(cal.getTime());
	}
}
