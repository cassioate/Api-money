package com.tessaro.moneyapi.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tessaro.moneyapi.event.RecursoCriadoEvent;
import com.tessaro.moneyapi.model.Categoria;
import com.tessaro.moneyapi.service.CategoriaService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;	

	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN') OR hasRole('GUEST')")
	@GetMapping
	public List<Categoria> findAll() {
		return service.findAll();
	}
	
	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Categoria>> findById(@PathVariable Long id) {
		Optional<Categoria> categoria = service.findById(id);
		return !categoria.isEmpty() ? ResponseEntity.ok().body(categoria) : ResponseEntity.notFound().build();		/* se a categoria existir então ele retornarar o que foi encontrado na busca, caso contrario ele retornarar Not Found (404) * .build() irá trazer o um response sem corpo (Que é necessario vir).  */
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Categoria> save (@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = service.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo())); /* this se refere ao objeto que criou o evento*/
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @Valid @RequestBody Categoria categoria){
		Categoria categoriaSalva = service.atualizar(id, categoria);
		return ResponseEntity.ok(categoriaSalva);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/nome")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeNome(@PathVariable Long id, @RequestBody String propriedade) {
		service.atualizarPropriedadeNome(id, propriedade);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/{propriedade}")
	public void atualizarErrado(@PathVariable Long id, @PathVariable String propriedade) throws NotFoundException{
		throw new NotFoundException("varaivel não existe");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		service.remover(id);
	}
}
