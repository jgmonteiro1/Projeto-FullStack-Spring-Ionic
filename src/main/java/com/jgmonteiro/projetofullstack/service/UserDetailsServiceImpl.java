package com.jgmonteiro.projetofullstack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jgmonteiro.projetofullstack.domain.Cliente;
import com.jgmonteiro.projetofullstack.repository.ClienteRepository;
import com.jgmonteiro.projetofullstack.security.UserSpringSecurity;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private ClienteRepository repository;
	
	//Permite buscar o usuário 
	//Recebe o usuário e retorna o UserDetails
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Cliente obj = repository.findByEmail(email);
		if(obj == null) {
			throw new UsernameNotFoundException(email);
		} 
		
		return new UserSpringSecurity(obj.getId(), obj.getEmail(), obj.getSenha(), obj.getPerfis());
	}

}
