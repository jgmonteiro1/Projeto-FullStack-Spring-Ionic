package com.jgmonteiro.projetofullstack.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	//Macete para importar valor da cheve no .properties
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();	
	}
	
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if(claims!=null) {
			//Se o claims for diferente de nulo significa que deu certo
			//Obtem o username a partir dos claims
			String username = claims.getSubject();
			//Obtem a data de expiracao a partir dos claims
			Date expirationDate = claims.getExpiration();
			//Var para representar a data atual
			Date now = new Date(System.currentTimeMillis());
			//Testa se o usuario eh diferente de nulo, se a data de expiracao eh diferente de nulo e por fim, se a data atual ainda eh anterior a data de expiracao
			if(username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}
	
	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if(claims !=null) {
			return claims.getSubject();
		}
		return null;
	}
	
	//funcao auxiliar para retornar claims a partir de um token
	private Claims getClaims(String token) {
		try {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch(Exception e) {
			//Se ao tentar pegar os claims do token for invalido ou deu algum problema retorna null
			return null;
		}
	}
}
