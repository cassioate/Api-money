package com.tessaro.moneyapi.resource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tessaro.moneyapi.event.RecursoCriadoEvent;
import com.tessaro.moneyapi.exceptionhandler.Erro;
import com.tessaro.moneyapi.model.Lancamento;
import com.tessaro.moneyapi.model.enumeration.LancamentoEnum;
import com.tessaro.moneyapi.repository.filter.LancamentoFilter;
import com.tessaro.moneyapi.service.LancamentoService;
import com.tessaro.moneyapi.service.exception.InexistenteOuInativoException;

import javassist.NotFoundException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LancamentoService service;
	
	@Autowired
	private ApplicationEventPublisher publisher; /* Utilizado para publicar o URI que é criado no RecursoCriadoEvent*/
	
	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	@GetMapping
	public Page<Lancamento> buscar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return service.buscar(lancamentoFilter, pageable);
	}
	
	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Lancamento>> findById(@PathVariable Long id) {
		Optional<Lancamento> lancamento = service.findById(id);
		return !lancamento.isEmpty() ? ResponseEntity.ok().body(lancamento) : ResponseEntity.notFound().build(); /* se a lancamento existir então ele retornarar o que foi encontrado na busca, caso contrario ele retornarar Not Found (404) * .build() irá trazer o um response sem corpo (Que é necessario vir).  */
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Lancamento> save (@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalva = service.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalva.getCodigo())); /* this se refere ao objeto que criou o evento*/
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long id, @Valid @RequestBody Lancamento lancamento){
		Lancamento lancamentoSalva = service.atualizar(id, lancamento);
		return ResponseEntity.ok(lancamentoSalva);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/descricao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarDescricao(@PathVariable Long id, @RequestBody String variavel){
		service.atualizarPropriedadeDescricao(id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/dataVencimento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarAtivo(@PathVariable Long id, @RequestBody LocalDate variavel){
		service.atualizarPropriedadeDataVencimento(id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/dataPagamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarDataPagamento(@PathVariable Long id, @RequestBody LocalDate variavel){
		service.atualizarPropriedadeDataPagamento(id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/valor")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarValor(@PathVariable Long id, @RequestBody BigDecimal variavel){
		service.atualizarPropriedadeValor(id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/observacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarObservacao(@PathVariable Long id, @RequestBody String variavel){
		service.atualizarPropriedadeObservacao(id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/tipo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarTipo(@PathVariable Long id, @RequestBody LancamentoEnum variavel){
		service.atualizarPropriedadeTipo(id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/pessoa")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPessoa(@PathVariable Long id, @RequestBody Long variavel){
		service.atualizarPropriedadePessoa(id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/categoria")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarCategoria(@PathVariable Long id, @RequestBody Long variavel){
		service.atualizarPropriedadeCategoria(id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		service.remover(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/{propriedade}")
	public void atualizarErrado(@PathVariable Long id, @PathVariable String propriedade) throws NotFoundException{
		throw new NotFoundException("varaivel não existe");
	}
	
	/* Por ser uma regra de negocio coloquei no proprio controller, para nao bagunçar o handler */
	@ExceptionHandler({InexistenteOuInativoException.class})
	public ResponseEntity<Object> handleInexistenteOuInativoException (InexistenteOuInativoException ex) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("inexistente.ou-inativo", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return ResponseEntity.badRequest().body(erros);
	}
	
}
