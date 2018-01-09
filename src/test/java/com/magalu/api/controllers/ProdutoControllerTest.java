package com.magalu.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magalu.api.dtos.DistanceDto;
import com.magalu.api.dtos.LojaDto;
import com.magalu.api.dtos.ProdutoDto;
import com.magalu.api.entities.Loja;
import com.magalu.api.entities.Produto;
import com.magalu.api.repositories.LojaRepository;
import com.magalu.api.services.ProdutoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProdutoControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private LojaRepository lojaRepository;

	@MockBean
	private ProdutoService produtoService;

	private static final String APIPATH = "/api/produto";
	private static final String DESCRICAO = "Produto Teste";
	private static final String CODIGO = "956456";
	private static final long ID = 32L;
	private static final BigDecimal VALOR = new BigDecimal("353.49");
	private static final String LOJA1_CODIGO = "987659";
	private static final String LOJA2_CODIGO = "987654";
	private static final String ORIGEM = "01102-000";
	
	@Before
	public void setUp() throws Exception {
		Loja loja1 = new Loja();
		Loja loja2 = new Loja();
		loja1.setDescricao("Magazine Luiza 1");
		loja1.setCodigo(LOJA1_CODIGO);
		loja1.setCep("01102-000");
		this.lojaRepository.save(loja1);
		
		loja2.setDescricao("Magazine Luiza 2");
		loja2.setCodigo(LOJA2_CODIGO);
		loja2.setCep("02033-050");
		this.lojaRepository.save(loja2);
		
	}

	@After
    public final void tearDown() {
		this.lojaRepository.deleteAll();
	}
	

	@Test
	@WithMockUser(username = "admin@magalu.com.br", roles = {"ADMIN"})
	public void cadastrarProduto() throws Exception {
		Produto produto = obterDadosProduto();
		BDDMockito.given(this.produtoService.buscaPorCodigo(Mockito.anyString())).willReturn(Optional.empty());
		BDDMockito.given(this.produtoService.persistir(Mockito.any(Produto.class))).willReturn(produto);

		mvc.perform(MockMvcRequestBuilders.post(APIPATH)
				.content(this.obterJsonRequisicaoPost(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.descricao").value(DESCRICAO))
				.andExpect(jsonPath("$.data.codigo").value(CODIGO))
				.andExpect(jsonPath("$.data.valor").value(VALOR))
				.andExpect(jsonPath("$.data.lojas[0].codigo").value(LOJA1_CODIGO))
				.andExpect(jsonPath("$.data.lojas[1].codigo").value(LOJA2_CODIGO))
				.andExpect(jsonPath("$.errors").isEmpty());;
	}
	
	@Test
	@WithMockUser(username = "admin@magalu.com.br", roles = {"ADMIN"})
	public void cadastrarProdutoDescricaoInvalida() throws Exception {
		Produto produto = obterDadosProduto();
		BDDMockito.given(this.produtoService.buscaPorCodigo(Mockito.anyString())).willReturn(Optional.empty());
		BDDMockito.given(this.produtoService.persistir(Mockito.any(Produto.class))).willReturn(produto);

		mvc.perform(MockMvcRequestBuilders.post(APIPATH)
				.content(this.obterJsonRequisicaoPost(Optional.of("AZ")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Descrição deve conter entre 3 e 150 caracteres."));
	}
	
	@Test
	@WithMockUser
	public void buscarProdutosCodigo() throws Exception {
		Produto produto = obterDadosProduto();
		DistanceDto distanceDto = new DistanceDto();
		distanceDto.setText("2,1 km");
		distanceDto.setValue("2100");
		BDDMockito.given(this.produtoService.buscaPorCodigo(Mockito.anyString())).willReturn(Optional.of(produto));
		BDDMockito.given(this.produtoService.buscaDistancia(Mockito.anyString(), Mockito.anyString())).willReturn(distanceDto);

		mvc.perform(MockMvcRequestBuilders.get(APIPATH + "/busca")
				.param("codigo", CODIGO)
				.param("origem", ORIGEM)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.valor").value(VALOR))
				.andExpect(jsonPath("$.data.lojas[0].codigo").value(LOJA1_CODIGO))
				.andExpect(jsonPath("$.data.lojas[1].codigo").value(LOJA2_CODIGO))
				.andExpect(jsonPath("$.data.lojas[0].distancia").value(distanceDto.getText()))
				.andExpect(jsonPath("$.data.lojas[1].distancia").value(distanceDto.getText()))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void buscarProdutosDescricao() throws Exception {
		Produto produto = obterDadosProduto();
		DistanceDto distanceDto = new DistanceDto();
		distanceDto.setText("2,1 km");
		distanceDto.setValue("2100");
		BDDMockito.given(this.produtoService.buscaPorCodigo(Mockito.anyString())).willReturn(Optional.of(produto));
		BDDMockito.given(this.produtoService.buscaPorDescricao(Mockito.anyString())).willReturn(Optional.of(produto));
		BDDMockito.given(this.produtoService.buscaDistancia(Mockito.anyString(), Mockito.anyString())).willReturn(distanceDto);

		mvc.perform(MockMvcRequestBuilders.get(APIPATH + "/busca")
				.param("descricao", DESCRICAO)
				.param("origem", ORIGEM)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.valor").value(VALOR))
				.andExpect(jsonPath("$.data.lojas[0].codigo").value(LOJA1_CODIGO))
				.andExpect(jsonPath("$.data.lojas[1].codigo").value(LOJA2_CODIGO))
				.andExpect(jsonPath("$.data.lojas[0].distancia").value(distanceDto.getText()))
				.andExpect(jsonPath("$.data.lojas[1].distancia").value(distanceDto.getText()))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	private Produto obterDadosProduto() {
		Produto produto = new Produto();
		produto.setId(32L);
		produto.setDescricao(DESCRICAO);
		produto.setCodigo(CODIGO);
		produto.setValor(VALOR);
		produto.setLojas(new ArrayList<Loja>());
		produto.getLojas().add(this.lojaRepository.findByCodigo(LOJA1_CODIGO));
		produto.getLojas().add(this.lojaRepository.findByCodigo(LOJA2_CODIGO));
		return produto;
	}

	private String obterJsonRequisicaoPost(Optional<String> descErr) throws JsonProcessingException {
		ProdutoDto produtoDto = new ProdutoDto();
		produtoDto.setId(null);
		produtoDto.setCodigo(CODIGO);
		produtoDto.setDescricao(Objects.isNull(descErr) ? DESCRICAO : descErr.get());
		produtoDto.setValor(VALOR);
		produtoDto.setLojas(new ArrayList<LojaDto>());
		produtoDto.getLojas().add(converterLojaDto(this.lojaRepository.findByCodigo(LOJA1_CODIGO)));
		produtoDto.getLojas().add(converterLojaDto(this.lojaRepository.findByCodigo(LOJA2_CODIGO)));
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(produtoDto);
	}

	private LojaDto converterLojaDto(Loja loja) {
		LojaDto lojaDto = new LojaDto();
		lojaDto.setId(loja.getId());
		lojaDto.setCep(loja.getCep());
		lojaDto.setCodigo(loja.getCodigo());
		lojaDto.setDescricao(loja.getDescricao());
		return lojaDto;
	}
	
	

}
