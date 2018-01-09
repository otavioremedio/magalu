package com.magalu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.magalu.api.entities.Produto;

@Transactional(readOnly = true)
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	Produto findByCodigo(String codigo);
	Produto findByDescricaoIgnoreCase(String descricao);
}
