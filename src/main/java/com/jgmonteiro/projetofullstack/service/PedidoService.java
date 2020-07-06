package com.jgmonteiro.projetofullstack.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgmonteiro.projetofullstack.domain.Categoria;
import com.jgmonteiro.projetofullstack.domain.ItemPedido;
import com.jgmonteiro.projetofullstack.domain.PagamentoComBoleto;
import com.jgmonteiro.projetofullstack.domain.Pedido;
import com.jgmonteiro.projetofullstack.domain.enums.EstadoPagamento;
import com.jgmonteiro.projetofullstack.repository.ItemPedidoRepository;
import com.jgmonteiro.projetofullstack.repository.PagamentoRepository;
import com.jgmonteiro.projetofullstack.repository.PedidoRepository;
import com.jgmonteiro.projetofullstack.service.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public List<Pedido> findAll(){
		return repository.findAll();
	}
	
	public Pedido findById(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		//Para garantir que eu realmente estou inserindo um novo pedido
		obj.setId(null);
		//Cria uma nova data a partir do instante atual
		obj.setInstante(new Date());
		obj.setCliente(clienteService.findById(obj.getCliente().getId()));
		//Definindo o estado do pagamento
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		//Associação para que o pagamento conheça o pedido dele
		obj.getPagamento().setPedido(obj);
		//Testa se o tipo do meu pagamento é o PagamentoComBoleto
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			//Variável recebe um casting do meu obj.getPagamento para garantir que o 'pag' é uma variável do tipo PagamentoComBoleto
			PagamentoComBoleto pag = (PagamentoComBoleto) obj.getPagamento();
			//Classe com método que irá preencher a data de vencimento
			boletoService.preencherPagamentoComBoleto(pag, obj.getInstante());
		}
		//Salvando o pedido
		obj = repository.save(obj);
		//Salvando o pagamento
		pagamentoRepository.save(obj.getPagamento());
		//Percorrendo todos os itens de pedidos associados ao meu obj
		for(ItemPedido x : obj.getItens()) {
			x.setDesconto(0.0);
			//Associando o item de pedido com o produto que está sendo buscado no banco de dados
			x.setProduto(produtoService.findById(x.getProduto().getId()));
			//Setando o preço do item de pedido como o mesmo preço do produto
			x.setPreco(x.getProduto().getPreco());
			//Associando esse item de pedido com o Pedido que estou inserindo
			x.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
	
	
}
