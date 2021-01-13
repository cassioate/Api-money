package com.tessaro.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tessaro.moneyapi.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
