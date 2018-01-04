package com.magalu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.magalu.api.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	
	@Transactional(readOnly = true)
	Conta findByEmail(String email);
}
