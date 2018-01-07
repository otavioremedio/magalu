package com.magalu.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

import com.magalu.api.dtos.BuscaDto;
import com.magalu.api.dtos.GoogleDto;
import com.magalu.api.dtos.LojaDto;
import com.magalu.api.dtos.ProdutoDto;
import com.magalu.api.entities.Loja;
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
	 * @throws Exception 
	 */
	@GetMapping(value = "/{codigo}/{descricao}/{origem}")
	public ResponseEntity<Response<BuscaDto>> buscarProduto(@PathVariable("codigo") String codigo, 
			@PathVariable("descricao") String descricao, @PathVariable("origem") String origem) throws Exception {
		log.info("Buscando produto e lojas");
		Response<BuscaDto> response = new Response<BuscaDto>();
		BuscaDto busca = new BuscaDto();
		Optional<Produto> produto = this.produtoService.buscaPorCodigo(codigo);

		if (!produto.isPresent()) {
			log.info("Produto não encontrado para o codigo: {}", codigo);
			response.getErrors().add("Produto não encontrado para o codigo " + codigo);
			return ResponseEntity.badRequest().body(response);
		}
		
		for (Loja loja : produto.get().getLojas()) {
			Optional<GoogleDto> googleDto = this.produtoService.buscaDistancia(origem, loja.getCep());
			
			try{
				if(googleDto.isPresent()){
					busca.getLojas().add(this.converterLojaDto(loja, googleDto));
				}
			} catch(Exception e){
				throw new Exception("Ocorreu um erro ao tentar calcular a distância entre a loja e o cliente." +
									" Verifique se o endereço ou cep estão corretos. O cep deve possuir um traço.");
			}
		}

		busca.setValor(produto.get().getValor().toString());
		response.setData(busca);
		return ResponseEntity.ok(response);
	}

	private LojaDto converterLojaDto(Loja loja, Optional<GoogleDto> googleDto) {
		LojaDto lojaDto = new LojaDto();
		lojaDto.setCep(loja.getCep());
		lojaDto.setCodigo(loja.getCodigo());
		lojaDto.setDescricao(loja.getDescricao());
		lojaDto.setDistancia(googleDto.get().getRows().get(0).getElements().get(0).getDistance().getText());
		return lojaDto;
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
