package com.magalu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.magalu.api.entities.Loja;

public interface LojaRepository extends JpaRepository<Loja, Long> {

	@Transactional(readOnly = true)
	Loja findByCodigo(String codigo);
}
