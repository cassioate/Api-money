package com.tessaro.moneyapi.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tessaro.moneyapi.model.Endereco;
import com.tessaro.moneyapi.model.Pessoa;
import com.tessaro.moneyapi.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;
	
	public List<Pessoa> findAll() {
		return repository.findAll();
	}
	
	public Optional<Pessoa> findById(Long id) {
		return repository.findById(id);
	}
	
	public Pessoa save(Pessoa pessoa) {
		repository.save(pessoa);
		return pessoa;
	}
	
	public Pessoa atualizar (Long id, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPeloId(id);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		repository.save(pessoaSalva);
		return pessoaSalva;
	}

	public void atualizarPropriedadeNome(Long id, String variavel) {
		Pessoa pessoaSalva = buscarPessoaPeloId(id);
		pessoaSalva.setNome(variavel);
		repository.save(pessoaSalva);
	}	
	
	public void atualizarPropriedadeAtivo(Long id, Boolean variavel) {
		Pessoa pessoaSalva = buscarPessoaPeloId(id);
		pessoaSalva.setAtivo(variavel);
		repository.save(pessoaSalva);
	}	
	
	public void atualizarPropriedadeEndereco(Long id, Endereco variavel) {
		Pessoa pessoaSalva = buscarPessoaPeloId(id);
		pessoaSalva.setEndereco(variavel);
		repository.save(pessoaSalva);
	}	
	
	public void remover (Long id) {
		repository.deleteById(id);
	}
	
	
				/* Metodos */
	
	public Pessoa buscarPessoaPeloId(Long id) {
		Pessoa pessoaSalva = repository.findById(id).get();
		if (pessoaSalva == null) {
			throw new EntityNotFoundException();
		}
		return pessoaSalva;
	}
}
