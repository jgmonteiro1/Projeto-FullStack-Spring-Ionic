package com.jgmonteiro.projetofullstack.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgmonteiro.projetofullstack.domain.Categoria;
import com.jgmonteiro.projetofullstack.domain.Cliente;
import com.jgmonteiro.projetofullstack.repository.ClienteRepository;
import com.jgmonteiro.projetofullstack.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	public List<Cliente> findAll(){
		List<Cliente> list = repository.findAll();
		return list;
	}

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
}