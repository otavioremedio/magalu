package com.magalu.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magalu.api.entities.Loja;
import com.magalu.api.repositories.LojaRepository;
import com.magalu.api.services.LojaService;

@Service
public class LojaServiceImpl implements LojaService {

	private static final Logger log = LoggerFactory.getLogger(LojaServiceImpl.class);

	@Autowired
	private LojaRepository lojaRepository;

	@Override
	public Loja persistir(Loja loja) {
		log.info("Persistindo loja: {}", loja);
		return this.lojaRepository.save(loja);
	}

	@Override
	public Optional<Loja> buscaPorCodigo(String codigo) {
		log.info("Buscando loja pelo codigo: {}", codigo);
		return Optional.ofNullable(this.lojaRepository.findByCodigo(codigo));
	}



}
