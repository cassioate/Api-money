package com.tessaro.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tessaro.moneyapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
