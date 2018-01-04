package com.magalu.api.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magalu.api.entities.Conta;
import com.magalu.api.response.Response;
import com.magalu.api.services.ContaService;

@RestController
@RequestMapping("/api/conta")
@CrossOrigin(origins = "*")
public class ContaController {

	private static final Logger log = LoggerFactory.getLogger(ContaController.class);

	@Autowired
	private ContaService contaService;

	public ContaController() {
	}	
}
