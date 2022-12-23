package com.dsCadastro.cliente.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dsCadastro.cliente.dto.ClientDTO;
import com.dsCadastro.cliente.entities.Client;
import com.dsCadastro.cliente.repository.ClientRepository;
import com.dsCadastro.cliente.service.exception.DatabaseException;
import com.dsCadastro.cliente.service.exception.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	//método que retorna os dados de todas os clientes
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
		
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(cli -> new ClientDTO(cli)); //cada cli é um objeto da lista retornada do banco
	}
	
	//método para retornar o cliente com o id mencionado
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		 Optional<Client> obj = repository.findById(id);
		 Client client = obj.orElseThrow(() -> new ResourceNotFoundException(String.format("Cliente com id %s não localizado.", id)));
		
		return new ClientDTO(client);
	}
	
	//método para salvar um novo cliente
	@Transactional
	public ClientDTO save(ClientDTO dto) {
		Client client = new Client();
		copyDtoToEntity(dto,client);
		client = repository.save(client);
		return new ClientDTO(client);
	}
	
	//método para atualizar cadastro de cliente
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client client =  repository.getOne(id); 
			copyDtoToEntity(dto, client);
			client = repository.save(client);
			return new ClientDTO(client);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(String.format("ID %s não localizado para atualização.", id));
		}
	}
	
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(String.format("ID %s não localizado para remoção.", id));
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(String.format("Cliente com id %s não pode ser removido, possui vinculo com entidades relacionadas.", id));
		}
		
	}
	

	//métodos auxiliares
	private void copyDtoToEntity(ClientDTO dto, Client client) {
		client.setBirthDate(dto.getBirthDate());
		client.setChildren(dto.getChildren());
		client.setCpf(dto.getCpf());
		client.setIncome(dto.getIncome());
		client.setName(dto.getName());
	}



	

}
