package com.jgmonteiro.projetofullstack.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

//Classe auxiliar para ajudar a trabalhar com os recursos
public class URL {
	
	//Método para converter uma String em uma lista de inteiras. Utilizado para receber as categorias por parâmetro
	public static List<Integer> decodeIntList(String s){
		//Vetor de String para receber o recorte da String do argumento com base na vírgula
		String [] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		//For para percorrer esse vetor
		for(int i = 0; i<vet.length;i++) {
			//Converte o elemento na posição i do meu vetor para um inteiro, o resultado será adicionado na minha lista de INT
			list.add(Integer.parseInt(vet[i]));
			
		}
		
		return list;
	}
	
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
