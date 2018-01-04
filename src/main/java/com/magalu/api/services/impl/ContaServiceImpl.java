package com.magalu.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magalu.api.entities.Conta;
import com.magalu.api.repositories.ContaRepository;
import com.magalu.api.services.ContaService;

@Service
public class ContaServiceImpl implements ContaService {
	
	private static final Logger log = LoggerFactory.getLogger(ContaServiceImpl.class);

	@Autowired
	private ContaRepository contaRepository;
	
	public Optional<Conta> buscarPorEmail(String email) {
		log.info("Buscando Conta pelo email {}", email);
		return Optional.ofNullable(this.contaRepository.findByEmail(email));
	}
	
}
