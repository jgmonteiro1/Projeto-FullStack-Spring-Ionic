package com.jgmonteiro.projetofullstack.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.jgmonteiro.projetofullstack.service.validation.ClienteInsert;

//DTO utilizado para inserir um novo cliente
@ClienteInsert
public class ClienteNewDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//Dados referentes ao novo cliente
	@NotEmpty(message = "Preencimento de campo obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120")
	private String nome;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "Deve ser inserido um e-mail ex: joao@dominio.com")
	private String email;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String cpfOuCnpj;
	
	private Integer tipo;
	
	//Dados referentes ao endereço do cliente
	@NotEmpty(message = "Preenchimento obrigatório")
	private String logradouro;
	@NotEmpty(message = "Preenchimento obrigatório")
	private String numero;
	private String complemento;
	private String bairro;
	@NotEmpty(message = "Preenchimento obrigatório")
	private String cep;
	
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String telefone1;
	//Telefones opcionais
	private String telefone2;
	private String telefone3;
	
	//Para cadastrar um novo cliente com seu endereço é necessário receber o código de uma cidade
	private Integer cidadeId;
	
	
	public ClienteNewDTO() {
		
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}


	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}


	public Integer getTipo() {
		return tipo;
	}


	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}


	public String getLogradouro() {
		return logradouro;
	}


	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getComplemento() {
		return complemento;
	}


	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public String getBairro() {
		return bairro;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getTelefone1() {
		return telefone1;
	}


	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}


	public String getTelefone2() {
		return telefone2;
	}


	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}


	public String getTelefone3() {
		return telefone3;
	}


	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}


	public Integer getCidadeId() {
		return cidadeId;
	}


	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}
	
	
	
}
