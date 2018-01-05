package com.magalu.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magalu.api.entities.Loja;
import com.magalu.api.entities.Produto;
import com.magalu.api.repositories.ProdutoRepository;
import com.magalu.api.services.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {
	
	private static final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public Produto persistir(Produto produto) {
		log.info("Persistindo produto: {}", produto);
		return this.produtoRepository.save(produto); 
	}

	@Override
	public Optional<Produto> buscaPorCodigo(String codigo) {
		log.info("Buscando produto pelo codigo: {}", codigo);
		return Optional.ofNullable(this.produtoRepository.findByCodigo(codigo));
	}
	
	
	
}
