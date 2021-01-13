package com.tessaro.moneyapi.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tessaro.moneyapi.model.Lancamento;
import com.tessaro.moneyapi.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public Page<Lancamento> filtrar(LancamentoFilter lancamento, Pageable pageable);

}
