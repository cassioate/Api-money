package com.tessaro.moneyapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tessaro.moneyapi.model.Categoria;
import com.tessaro.moneyapi.model.Lancamento;
import com.tessaro.moneyapi.model.Pessoa;
import com.tessaro.moneyapi.model.enumeration.LancamentoEnum;
import com.tessaro.moneyapi.repository.CategoriaRepository;
import com.tessaro.moneyapi.repository.LancamentoRepository;
import com.tessaro.moneyapi.repository.PessoaRepository;
import com.tessaro.moneyapi.repository.filter.LancamentoFilter;
import com.tessaro.moneyapi.service.exception.InexistenteOuInativoException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private CategoriaRepository repositoryCategoria;
	
	@Autowired
	private PessoaRepository repositoryPessoa;
	
	public Page<Lancamento> buscar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return repository.filtrar(lancamentoFilter,pageable);
	}
	
	public Optional<Lancamento> findById(Long id) {
		return repository.findById(id);
	}
	
	public Lancamento save(Lancamento lancamento) {
		Pessoa pessoa = repositoryPessoa.findById(lancamento.getPessoa().getCodigo()).get();
		if (pessoa == null || !pessoa.getAtivo() ) {
			throw new InexistenteOuInativoException();
		}
		repository.save(lancamento);
		return lancamento;
	}
	
	public Lancamento atualizar (Long id, Lancamento lancamento) {
		Lancamento lancamentoSalva = buscarLancamentoPeloId(id);
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");
		repository.save(lancamentoSalva);
		return lancamentoSalva;
	}

	public void atualizarPropriedadeDescricao(Long id, String variavel) {
		Lancamento lancamentoSalva = buscarLancamentoPeloId(id);
		lancamentoSalva.setDescricao(variavel);
		repository.save(lancamentoSalva);
	}	
	
	public void atualizarPropriedadeDataVencimento(Long id, LocalDate variavel) {
		Lancamento lancamentoSalva = buscarLancamentoPeloId(id);
		lancamentoSalva.setDataVencimento(variavel);
		repository.save(lancamentoSalva);
	}	
	
	public void atualizarPropriedadeDataPagamento(Long id, LocalDate variavel) {
		Lancamento lancamentoSalva = buscarLancamentoPeloId(id);
		lancamentoSalva.setDataPagamento(variavel);
		repository.save(lancamentoSalva);
	}
	
	public void atualizarPropriedadeValor(Long id, BigDecimal variavel) {
		Lancamento lancamentoSalva = buscarLancamentoPeloId(id);
		lancamentoSalva.setValor(variavel);
		repository.save(lancamentoSalva);
	}
	
	public void atualizarPropriedadeObservacao(Long id, String variavel) {
		Lancamento lancamentoSalva = buscarLancamentoPeloId(id);
		lancamentoSalva.setObservacao(variavel);
		repository.save(lancamentoSalva);
	}
	
	public void atualizarPropriedadeTipo(Long id, LancamentoEnum variavel) {
		Lancamento lancamentoSalva = buscarLancamentoPeloId(id);
		lancamentoSalva.setTipo(variavel);
		repository.save(lancamentoSalva);
	}
	
	public void atualizarPropriedadePessoa(Long id, Long variavel) {
		Lancamento lancamentoSalva = buscarLancamentoPeloId(id);
		Pessoa pessoaSalva = repositoryPessoa.findById(variavel).get();
		lancamentoSalva.setPessoa(pessoaSalva);
		repository.save(lancamentoSalva);
	}
	
	public void atualizarPropriedadeCategoria(Long id, Long variavel) {
		Lancamento lancamentoSalva = buscarLancamentoPeloId(id);
		Categoria categoriaSalva = repositoryCategoria.findById(variavel).get();
		lancamentoSalva.setCategoria(categoriaSalva);
		repository.save(lancamentoSalva);
	}
	
	public void remover (Long id) {
		repository.deleteById(id);
	}
	
	
				/* Metodos */
	
	private Lancamento buscarLancamentoPeloId(Long id) {
		Lancamento lancamentoSalva = repository.findById(id).get();
		if (lancamentoSalva == null) {
			throw new EntityNotFoundException();
		}
		return lancamentoSalva;
	}
	
}
