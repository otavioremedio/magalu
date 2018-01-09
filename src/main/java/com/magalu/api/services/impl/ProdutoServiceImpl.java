package com.magalu.api.services.impl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.util.Locale;
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
	public String buscaDistancia(String origem, String destino) {
		log.info("Buscando distancia entre {} e {}", origem, destino);
		RestTemplate restTemplate = new RestTemplate();
		DecimalFormat formatter = new DecimalFormat("#,###.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));
		GoogleDto googleDto = new GoogleDto();
		
		ResponseEntity<GoogleDto> googleResponse = restTemplate.exchange(MessageFormat.format(googleApi, origem, destino)
	    , HttpMethod.GET, null, new ParameterizedTypeReference<GoogleDto>(){});
		
		googleDto = googleResponse.getBody();
		String distancia = googleDto.getRows().get(0).getElements().get(0).getDistance().getText();
		return formatter.format(Double.parseDouble(distancia.split(" ")[0])) + " " + distancia.split(" ")[1];
	}

	@Override
	public Optional<Produto> buscaPorDescricao(String descricao) {
		log.info("Buscando produto pela descricao: {}", descricao);
		return Optional.ofNullable(this.produtoRepository.findByDescricaoIgnoreCase(descricao));

	}
	
	
	
}
