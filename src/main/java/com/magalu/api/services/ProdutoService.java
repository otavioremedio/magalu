package com.magalu.api.services;

import java.util.Optional;

import com.magalu.api.dtos.DistanceDto;
import com.magalu.api.entities.Produto;

public interface ProdutoService {
	
	/**
	 * Cadastra uma novo produto na base de dados.
	 * 
	 * @param produto
	 * @return Produto
	 */
	Produto persistir(Produto produto);
	
	/**
	 * Procura produto por codigo.
	 * 
	 * @param codigo
	 * @return Produto
	 */	
	Optional<Produto> buscaPorCodigo(String codigo);
	
	/**
	 * Procura produto por descricao.
	 * 
	 * @param descricao
	 * @return Produto
	 */	
	Optional<Produto> buscaPorDescricao(String descricao);
	
	/**
	 * Procura distancia por cep.
	 * 
	 * @param cepOrigem e cepDestino
	 * @return DistanceDto
	 */	
	DistanceDto buscaDistancia(String origem, String destino);
}
