package com.magalu.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magalu.api.dtos.LojaDto;
import com.magalu.api.entities.Loja;
import com.magalu.api.response.Response;
import com.magalu.api.services.LojaService;

@RestController
@RequestMapping("/api/loja")
@CrossOrigin(origins = "*")
public class LojaController {

	private static final Logger log = LoggerFactory.getLogger(LojaController.class);

	@Autowired
	private LojaService lojaService;

	public LojaController() {
	}	
	
	/**
	 * Cadastra uma loja no sistema
	 * 
	 * @param lojaDto
	 * @param result
	 * @return ResponseEntity<Response<LojaDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<LojaDto>> cadastrar(@Valid @RequestBody LojaDto lojaDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando loja: {}", lojaDto.toString());
		Response<LojaDto> response = new Response<LojaDto>();

		validarDadosExistentes(lojaDto, result);
		Loja loja = this.converterDtoParaLoja(lojaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.lojaService.persistir(loja);

		response.setData(this.converterLojaDto(loja));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Verifica se a loja está cadastrada.
	 * 
	 * @param lojaDto
	 * @param result
	 */
	private void validarDadosExistentes(LojaDto lojaDto, BindingResult result) {
		this.lojaService.buscaPorCodigo(lojaDto.getCodigo())
			.ifPresent(func -> result.addError(new ObjectError("loja", "Já existe uma loja com esse código.")));
		
	}
	
	/**
	 * Converte os dados do DTO para loja.
	 * 
	 * @param lojaDto
	 * @param result
	 * @return Loja
	 * @throws NoSuchAlgorithmException
	 */
	private Loja converterDtoParaLoja(LojaDto lojaDto, BindingResult result)
			throws NoSuchAlgorithmException {
		Loja loja = new Loja();
		
		//setar do dto para o objeto
		return loja;
	}
	
	/**
	 * Popula o DTO de cadastro com os dados da loja.
	 * 
	 * @param loja
	 * @return LojaDto
	 */
	private LojaDto converterLojaDto(Loja funcionario) {
		LojaDto lojaDto = new LojaDto();
		

		//setear dto com dados do objeto

		return lojaDto;
	}
}
