package com.magalu.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magalu.api.dtos.BuscaDto;
import com.magalu.api.dtos.DistanceDto;
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
	@GetMapping(value = "/busca")
	public ResponseEntity<Response<BuscaDto>> buscarProduto(@RequestParam("codigo") Optional<String> codigo,
			@RequestParam("descricao") Optional<String> descricao, @RequestParam("origem") String origem) {
		log.info("Buscando produto e lojas");
		Response<BuscaDto> response = new Response<BuscaDto>();
		BuscaDto busca = new BuscaDto();
		Optional<Produto> produto;
		DistanceDto distanceDto;

		if(codigo.isPresent() && codigo.get().length() > 0){
			produto = this.produtoService.buscaPorCodigo(codigo.get());
		} else {
			produto = this.produtoService.buscaPorDescricao(descricao.get());
		}


		if (!produto.isPresent()) {
			log.info("Produto não encontrado para {}", codigo.isPresent() ? codigo.get().length() > 0 ? "o código " + codigo.get() : "a descrição " + descricao.get() : "a descrição " + descricao.get());
			response.getErrors().add("Produto não encontrado para " + (codigo.isPresent() ? codigo.get().length() > 0 ? "o código " + codigo.get() : "a descrição " + descricao.get() : "a descrição " + descricao.get()));
			return ResponseEntity.badRequest().body(response);
		}

		for (Loja loja : produto.get().getLojas()) {
			try {
				distanceDto = this.produtoService.buscaDistancia(origem, loja.getCep());
				if(distanceDto != null){
					busca.getLojas().add(this.converterLojaDto(loja, distanceDto.getText(), distanceDto.getValue()));
				}
			} catch (Exception e) {
				response.getErrors().add("Ocorreu um erro ao tentar calcular a distância entre a loja e o cliente." +
						 "Verifique se o endereço ou cep estão corretos. O cep deve possuir um traço.");
				return ResponseEntity.badRequest().body(response);
			}
		}
				
		busca.getLojas().sort(criaComparator());
		
		busca.setValor(produto.get().getValor().toString());
		response.setData(busca);
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

		response.setData(this.converterProdutoDto(this.produtoService.persistir(produto)));
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
		produto.setLojas(new ArrayList<Loja>());

		for (LojaDto lojaDto : produtoDto.getLojas()) {
			produto.getLojas().add(this.converterDtoLoja(lojaDto));
		}

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
		produtoDto.setId(produto.getId());
		produtoDto.setDescricao(produto.getDescricao());
		produtoDto.setCodigo(produto.getCodigo());
		produtoDto.setValor(produto.getValor());
		produtoDto.setLojas(new ArrayList<LojaDto>());

		for (Loja loja : produto.getLojas()) {
			produtoDto.getLojas().add(converterLojaDto(loja, null, null));
		}

		return produtoDto;
	}

	/**
	 * Popula o DTO da loja para retornar na busca.
	 *
	 * @param loja, googleDto
	 * @return LojaDto
	 */
	private LojaDto converterLojaDto(Loja loja, String distancia, String distanciaValor) {
		LojaDto lojaDto = new LojaDto();
		lojaDto.setId(loja.getId());
		lojaDto.setCep(loja.getCep());
		lojaDto.setCodigo(loja.getCodigo());
		lojaDto.setDescricao(loja.getDescricao());
		lojaDto.setDistancia(distancia);
		lojaDto.setDistanciaValor(distanciaValor);
		return lojaDto;
	}

	/**
	 * Converte os dados do DTO para loja.
	 *
	 * @param lojaDto
	 * @param result
	 * @return Loja
	 * @throws NoSuchAlgorithmException
	 */
	private Loja converterDtoLoja(LojaDto lojaDto) {
		Loja loja = new Loja();
		loja.setId(lojaDto.getId());
		loja.setDescricao(lojaDto.getDescricao());
		loja.setCodigo(lojaDto.getCodigo());
		loja.setCep(lojaDto.getCep());
		return loja;
	}
	
	private Comparator<LojaDto> criaComparator() {
		
		NumberFormat format = NumberFormat.getInstance(Locale.US);
			
		Comparator<LojaDto> distanciaComparator = (b1,b2) -> Integer.compare(Integer.parseInt(b1.getDistanciaValor()), Integer.parseInt(b2.getDistanciaValor()));
		return distanciaComparator;
	}

}
