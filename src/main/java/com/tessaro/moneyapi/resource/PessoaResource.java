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
import com.tessaro.moneyapi.model.Endereco;
import com.tessaro.moneyapi.model.Pessoa;
import com.tessaro.moneyapi.service.PessoaService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService service;
	
	@Autowired
	private ApplicationEventPublisher publisher; /* Utilizado para publicar o URI que é criado no RecursoCriadoEvent*/
	
	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	@GetMapping
	public List<Pessoa> findAll() {
		return service.findAll();
	}
	
	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Pessoa>> findById(@PathVariable Long id) {
		Optional<Pessoa> pessoa = service.findById(id);
		return !pessoa.isEmpty() ? ResponseEntity.ok().body(pessoa) : ResponseEntity.notFound().build(); /* se a pessoa existir então ele retornarar o que foi encontrado na busca, caso contrario ele retornarar Not Found (404) * .build() irá trazer o um response sem corpo (Que é necessario vir).  */
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Pessoa> save (@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = service.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo())); /* this se refere ao objeto que criou o evento*/
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = service.atualizar(id, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/nome")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarNome(@PathVariable Long id, @RequestBody String variavel){
		service.atualizarPropriedadeNome(id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarAtivo(@PathVariable Long id, @RequestBody Boolean variavel){
		service.atualizarPropriedadeAtivo(id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/endereco")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarEndereco(@PathVariable Long id, @RequestBody Endereco variavel){
		service.atualizarPropriedadeEndereco(id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		service.remover(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/{propriedade}")
	public void atualizarErrado(@PathVariable Long id, @PathVariable String propriedade) throws NotFoundException{
		throw new NotFoundException("varaivel não existe");
	}
	
}
