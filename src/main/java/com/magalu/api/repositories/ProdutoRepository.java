package com.magalu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.magalu.api.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Transactional(readOnly = true)
	Produto findByCodigo(String codigo);
}
