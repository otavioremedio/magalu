package com.magalu.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magalu.api.dtos.GoogleDto;
import com.magalu.api.dtos.ProdutoDto;
import com.magalu.api.entities.Produto;
import com.magalu.api.response.Response;
import com.magalu.api.services.ProdutoService;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin(origins = "*")
public class ProdutoController {

	private static final Logger log = LoggerFactory.getLogger(ProdutoController.class);

	@Autowired
	private ProdutoService produtoService;

	public ProdutoController() {
	}
	
	/**
	 * Retorna o produto buscado e as lojas que possuem.
	 * 
	 * @return ResponseEntity<Response<ProdutoDto>>
	 */
	@GetMapping(value = "/{codigo}/{descricao}/{origem}")
	public ResponseEntity<Response<ProdutoDto>> buscarProduto(@PathVariable("codigo") String codigo, 
			@PathVariable("descricao") String descricao, @PathVariable("origem") String origem) {
		log.info("Buscando produto e lojas");
		Response<ProdutoDto> response = new Response<ProdutoDto>();
		Optional<GoogleDto> googleDto = this.produtoService.buscaDistancia(origem, origem);
		
//		if (!loja.isPresent()) {
//			log.info("Loja não encontrada para o codigo: {}", codigo);
//			response.getErrors().add("Loja não encontrada para o codigo " + codigo);
//			return ResponseEntity.badRequest().body(response);
//		}

//		response.setData(this.converterLojaDto(loja.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Cadastra uma produto no sistema
	 *
	 * @param produtoDto
	 * @param result
	 * @return ResponseEntity<Response<produtoDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<ProdutoDto>> cadastrar(@Valid @RequestBody ProdutoDto produtoDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando produto: {}", produtoDto.toString());
		Response<ProdutoDto> response = new Response<ProdutoDto>();

		validarDadosExistentes(produtoDto, result);
		Produto produto = this.converterDtoParaProduto(produtoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.produtoService.persistir(produto);

		response.setData(this.converterProdutoDto(produto));
		return ResponseEntity.ok(response);
	}

	/**
	 * Verifica se o produto está cadastrado.
	 *
	 * @param produtoDto
	 * @param result
	 */
	private void validarDadosExistentes(ProdutoDto produtoDto, BindingResult result) {
		this.produtoService.buscaPorCodigo(produtoDto.getCodigo())
			.ifPresent(prod -> result.addError(new ObjectError("Produto", "Já existe uma Produto com esse código.")));

	}

	/**
	 * Converte os dados do DTO para Produto.
	 *
	 * @param produtoDto
	 * @param result
	 * @return Produto
	 * @throws NoSuchAlgorithmException
	 */
	private Produto converterDtoParaProduto(ProdutoDto produtoDto, BindingResult result)
			throws NoSuchAlgorithmException {
		Produto produto = new Produto();
		produto.setDescricao(produtoDto.getDescricao());
		produto.setCodigo(produtoDto.getCodigo());
		produto.setValor(produtoDto.getValor());
		produto.setLojas(produtoDto.getLojas());
		return produto;
	}

	/**
	 * Popula o DTO de cadastro com os dados do produto.
	 *
	 * @param produto
	 * @return ProdutoDto
	 */
	private ProdutoDto converterProdutoDto(Produto produto) {
		ProdutoDto produtoDto = new ProdutoDto();
		produtoDto.setDescricao(produto.getDescricao());
		produtoDto.setCodigo(produto.getCodigo());
		produtoDto.setValor(produto.getValor());
		produtoDto.setLojas(produto.getLojas());
		return produtoDto;
	}
}
