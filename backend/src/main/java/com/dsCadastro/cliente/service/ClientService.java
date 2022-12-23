package com.dsCadastro.cliente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dsCadastro.cliente.dto.ClientDTO;
import com.dsCadastro.cliente.entities.Client;
import com.dsCadastro.cliente.repository.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	//método que retorna os dados de todas as categorias páginados
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
		
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(cli -> new ClientDTO(cli)); //cada cli é um objeto da lista retornada do banco
	}

}
