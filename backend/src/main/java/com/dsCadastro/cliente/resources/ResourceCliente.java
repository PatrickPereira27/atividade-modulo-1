package com.dsCadastro.cliente.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@GetMapping("/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
		
		ClientDTO client = service.findById(id);
		
		return ResponseEntity.ok().body(client);
	}
	
	//salva e retorna o cliente salvo
	@PostMapping()
	public ResponseEntity<ClientDTO> save(@RequestBody ClientDTO dto){	
		dto = service.save(dto);
		
		//comando para formar o location com o recurso criado, que seria o clients/id o id será do novo objeto salvo
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(dto.getId()).toUri();
		
		//retorna 201 pra falar que o recurso foi criado, e também retorna o location por conta de boas praticas
		return ResponseEntity.created(uri).body(dto);
	}
	
	//atualiza o cadastro de cliente
	@PutMapping("/{id}")
	public ResponseEntity<ClientDTO> update(@RequestBody ClientDTO dto, @PathVariable Long id){	
	
		dto = service.update(id,dto);
		return ResponseEntity.ok().body(dto);
	}	
	
	//método para excluir cliente
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> delete( @PathVariable Long id){	
		service.delete(id);
		return ResponseEntity.noContent().build(); //retorna 204 - quer dizer que deu certo e o corpo da resposta está vazio
	}
	
	

}
