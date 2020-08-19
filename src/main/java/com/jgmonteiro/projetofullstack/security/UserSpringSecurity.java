package com.jgmonteiro.projetofullstack.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jgmonteiro.projetofullstack.domain.enums.Perfil;

public class UserSpringSecurity implements UserDetails{
	
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String email;
	private String senha;
	private  Collection<? extends GrantedAuthority> authorities;
	
	public UserSpringSecurity() {
		
	}
	
	
	/*
	public UserSpringSecurity(Integer id, String email, String senha,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = authorities;
	}*/
	
	
	public UserSpringSecurity(Integer id, String email, String senha,
			Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		//Gera a lista de autorizados a partir da lista de perfis
		this.authorities = perfis.stream().map( x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}


	public Integer getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	//Método para verificar se a conta está expirada. Por padrão será retornado verdadeiro para informar que ela não está expirada
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
