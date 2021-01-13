package com.tessaro.moneyapi.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tessaro.moneyapi.model.Categoria;
import com.tessaro.moneyapi.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public List<Categoria> findAll() {
		return repository.findAll();
	}

	public Optional<Categoria> findById(Long id) {
		return repository.findById(id);
	}

	public Categoria save(Categoria categoria) {
		repository.save(categoria);
		return categoria;
	}

	public Categoria atualizar(Long id, Categoria categoria) {
		Categoria categoriaSalva = buscarPessoaPeloId(id);
		BeanUtils.copyProperties(categoria, categoriaSalva, "codigo");
		repository.save(categoriaSalva);
		return categoriaSalva;
	}

	public void atualizarPropriedadeNome(Long id, String propriedade) {
		Categoria categoriaSalva = repository.findById(id).get();
		categoriaSalva.setNome(propriedade);
	}

	public void remover(Long id) {
		repository.deleteById(id);
	}

				/* Metodos */

	private Categoria buscarPessoaPeloId(Long id) {
		Categoria categoriaSalva = repository.findById(id).get();
		if (categoriaSalva == null) {
			throw new EntityNotFoundException();
		}
		return categoriaSalva;
	}

}
