package com.magalu.api.services;

import java.util.Optional;

import com.magalu.api.dtos.GoogleDto;
import com.magalu.api.entities.Produto;

public interface ProdutoService {
	
	/**
	 * Cadastra uma novo produto na base de dados.
	 * 
	 * @param produto
	 * @return Produto
	 */
	Produto persistir(Produto empresa);
	
	/**
	 * Procura produto por codigo.
	 * 
	 * @param codigo
	 * @return Produto
	 */	
	Optional<Produto> buscaPorCodigo(String codigo);
	
	/**
	 * Procura distancia por cep.
	 * 
	 * @param cepOrigem e cepDestino
	 * @return GoogleDto
	 */	
	Optional<GoogleDto> buscaDistanciaPorCep(String cepOrigem, String cepDestino);
}
