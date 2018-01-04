package com.magalu.api.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.magalu.api.entities.Conta;
import com.magalu.api.security.JwtUserFactory;
import com.magalu.api.services.ContaService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ContaService contaService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Conta> conta = contaService.buscarPorEmail(username);

		if (conta.isPresent()) {
			return JwtUserFactory.create(conta.get());
		}

		throw new UsernameNotFoundException("Email não encontrado.");
	}

}
