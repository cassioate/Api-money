package com.tessaro.moneyapi.config.security.user;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tessaro.moneyapi.config.security.user.dto.UserDTO;
import com.tessaro.moneyapi.event.RecursoCriadoEvent;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void cadastrar (@Valid @RequestBody UserDTO user, HttpServletResponse response) {
		UserDTO usuario = service.salvar(user);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuario.getId())); 
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{perfil}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cadastrarPerfil (@RequestBody ObjectNode objectNode , HttpServletResponse response) {
		String email = objectNode.get("email").asText();
		Integer perfil = objectNode.get("perfil").asInt();
		service.cadastrarPerfil(email, perfil);
	}
}
