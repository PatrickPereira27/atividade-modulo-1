package com.dsCadastro.cliente.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dsCadastro.cliente.dto.ClientDTO;
import com.dsCadastro.cliente.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ResourceCliente {
	
	@Autowired
	private ClientService service;
	
	//retorna todos os clientes
	@GetMapping()
	public ResponseEntity<Page<ClientDTO>> findAll(	
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
		@RequestParam(value = "direction", defaultValue = "ASC") String direction,
		@RequestParam(value = "orderBy", defaultValue = "name") String orderBy
	){	
		//page 		   = numero da pagina
		//linesPerPage = quantidade de linhas por página
		//direction    = tipo de ordenação ASC ou DESC
		//orderBy 	   = define o nome que ordenara os dados
		
		
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
		Page<ClientDTO> list = service.findAllPaged(pageRequest);
				
		return ResponseEntity.ok().body(list);
	}

}
