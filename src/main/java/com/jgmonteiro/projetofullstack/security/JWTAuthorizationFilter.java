package com.jgmonteiro.projetofullstack.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private JWTUtil jwtUtil;
	
	private UserDetailsService detailsService;
	
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService detailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.detailsService = detailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
		//Armazena o valor do header Authorization que veio da requisicao na variavel 'header'
		String header = request.getHeader("Authorization");
		
		if(header != null) {						   //Método auxiliar que irá receber o token e retornar um objeto do tipo UsernamePasswordAuthenticationToken que é do springsecurity
			UsernamePasswordAuthenticationToken auth = getAuthentication(request, header);
			//testa se o objeto construído eh diferente de nulo se ele for nulo significa que o token eh invalido
			if(auth != null) {
				//funcao para liberar o acesso no filtro
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		//informa pro filtro que ele pode continuar a requisicao
		chain.doFilter(request, response);
	}
	
	//Método retorna nulo quando o token for inválido
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) {
		//Se o token for valido
		if(jwtUtil.tokenValido(token)) {
							  //funcao para pegar o username dentro do token
			String username = jwtUtil.getUsername(token);
			//busca no banco de dados 
			UserDetails user = detailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}

}
