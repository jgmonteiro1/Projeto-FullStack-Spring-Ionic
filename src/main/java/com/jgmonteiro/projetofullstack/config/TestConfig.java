package com.jgmonteiro.projetofullstack.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jgmonteiro.projetofullstack.domain.Categoria;
import com.jgmonteiro.projetofullstack.domain.Cidade;
import com.jgmonteiro.projetofullstack.domain.Cliente;
import com.jgmonteiro.projetofullstack.domain.Endereco;
import com.jgmonteiro.projetofullstack.domain.Estado;
import com.jgmonteiro.projetofullstack.domain.Produto;
import com.jgmonteiro.projetofullstack.domain.enums.TipoCliente;
import com.jgmonteiro.projetofullstack.repository.CategoriaRepository;
import com.jgmonteiro.projetofullstack.repository.CidadeRepository;
import com.jgmonteiro.projetofullstack.repository.ClienteRepository;
import com.jgmonteiro.projetofullstack.repository.EnderecoRepository;
import com.jgmonteiro.projetofullstack.repository.EstadoRepository;
import com.jgmonteiro.projetofullstack.repository.ProdutoRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Override
	public void run(String... args) throws Exception {
				
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		Estado est1 = new Estado(null, "Bahia");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Salvador", est1);
		Cidade c2 = new Cidade(null, "Lauro de Freitas", est1);
		Cidade c3 = new Cidade(null, "São Paulo", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1,c2));
		est2.getCidades().add(c3);
		
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().add(p2);
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("40028922","30221515"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "14", "Casa dos fundos", "Nordeste", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
	}

}
