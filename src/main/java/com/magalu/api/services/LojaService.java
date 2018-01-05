package com.magalu.api.services;

import java.util.Optional;

import com.magalu.api.entities.Loja;

public interface LojaService {
	
	/**
	 * Cadastra uma nova loja na base de dados.
	 * 
	 * @param loja
	 * @return Loja
	 */
	Loja persistir(Loja empresa);
	
	/**
	 * Procura loja por codigo.
	 * 
	 * @param codigo
	 * @return Loja
	 */	
	Optional<Loja> buscaPorCodigo(String codigo);
}
