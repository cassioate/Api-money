package com.tessaro.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tessaro.moneyapi.model.Lancamento;
import com.tessaro.moneyapi.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{

}
