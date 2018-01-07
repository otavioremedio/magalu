package com.magalu.api.services.impl;

import java.text.MessageFormat;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.magalu.api.dtos.GoogleDto;
import com.magalu.api.entities.Produto;
import com.magalu.api.repositories.ProdutoRepository;
import com.magalu.api.services.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {
	
	private static final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Value("${google.api}")
	private String googleApi;

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

	@Override
	public Optional<GoogleDto> buscaDistancia(String origem, String destino) {
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<GoogleDto> googleResponse = restTemplate.exchange(MessageFormat.format(googleApi, origem, destino)
	    , HttpMethod.GET, null, new ParameterizedTypeReference<GoogleDto>(){});

		return Optional.ofNullable(googleResponse.getBody());
	}
	
	
	
}
