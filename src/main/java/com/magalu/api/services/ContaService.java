package com.magalu.api.services;

import java.util.Optional;

import com.magalu.api.entities.Conta;

public interface ContaService {
	
	/**
	 * Busca e retorna uma Conta dado um email.
	 * 
	 * @param email
	 * @return Optional<Conta>
	 */
	Optional<Conta> buscarPorEmail(String email);
	
}
