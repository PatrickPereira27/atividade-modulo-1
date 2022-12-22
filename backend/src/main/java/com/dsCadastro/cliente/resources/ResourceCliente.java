package com.dsCadastro.cliente.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ResourceCliente {
	
	@GetMapping
	public String teste() {
		return "funcionou!";
	}

}
