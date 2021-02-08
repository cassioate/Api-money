package com.tessaro.moneyapi.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tessaro.moneyapi.model.Lancamento;
import com.tessaro.moneyapi.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{
	
//	@Query("select obra from Obra obra where obra.dataPublicacao between :dataInicial and :dataFinal")
//	List<Obra> findByData(@Param("dataInicial")LocalDate dataInicial, @Param("dataFinal")LocalDate dataFinal);
	
	@Query("select l from Lancamento l where l.dataVencimento between :inicio and :fim and (:descricao is null or l.descricao like %:descricao%)")
	Page<Lancamento> findByParams(Pageable pageable, @Param("descricao")String descricao ,@Param("inicio")LocalDate dataVencimentoInicio, @Param("fim")LocalDate dataVencimentoFim);

}
//(:name is null or c.name = :name)
//(:descricao is null or l.descricao like %:descricao%)