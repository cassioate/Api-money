package com.tessaro.moneyapi.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tessaro.moneyapi.model.Lancamento;
import com.tessaro.moneyapi.model.dto.LancamentoDTO;
import com.tessaro.moneyapi.repository.filter.LancamentoFilter;
import com.tessaro.moneyapi.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {

	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
	
}
