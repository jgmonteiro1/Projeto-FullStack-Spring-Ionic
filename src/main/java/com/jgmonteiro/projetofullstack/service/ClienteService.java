package com.jgmonteiro.projetofullstack.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jgmonteiro.projetofullstack.domain.Categoria;
import com.jgmonteiro.projetofullstack.domain.Cidade;
import com.jgmonteiro.projetofullstack.domain.Cliente;
import com.jgmonteiro.projetofullstack.domain.Endereco;
import com.jgmonteiro.projetofullstack.domain.enums.TipoCliente;
import com.jgmonteiro.projetofullstack.dto.ClienteDTO;
import com.jgmonteiro.projetofullstack.dto.ClienteNewDTO;
import com.jgmonteiro.projetofullstack.repository.ClienteRepository;
import com.jgmonteiro.projetofullstack.repository.EnderecoRepository;
import com.jgmonteiro.projetofullstack.service.exceptions.DataIntegrityViolationException;
import com.jgmonteiro.projetofullstack.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<Cliente> findAll(){
		List<Cliente> list = repository.findAll();
		return list;
	}

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}
	
	public void deleteById(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível fazer a deleção");
		}
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	//Sobrecarga do método 'FromDTO' para utilizar um ClienteNewDTO
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente obj = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.converteParaEnum(objDto.getTipo()));
		Cidade cidade = new Cidade(objDto.getCidadeId(), null,null);
		Endereco endereco = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), obj, cidade);
		obj.getEnderecos().add(endereco);
		obj.getTelefones().add(objDto.getTelefone1());
		
		//Testa se o usuário informou telefones 2 e 3 que são opcionais, caso tenha sido inserido é instanciado um telefone2
		if(objDto.getTelefone2()!=null) {
			obj.getTelefones().add(objDto.getTelefone2());
		} 
		
		if(objDto.getTelefone3()!=null) {
			obj.getTelefones().add(objDto.getTelefone3());
		}
		
		return obj;
	}
	
	//Método auxiliar da classe para atualizar um objeto existente no banco de dados
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}