package com.magalu.api.controllers;

import static org.mockito.Matchers.isNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Objects;
import java.util.Optional;

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
import com.magalu.api.dtos.LojaDto;
import com.magalu.api.entities.Loja;
import com.magalu.api.services.LojaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LojaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private LojaService lojaService;

	private static final String APIPATH = "/api/loja";
	private static final String CEP = "01102-000";
	private static final String CODIGO = "956456";
	private static final long ID = 32L;
	private static final String DESCRICAO = "Loja Teste";
	

	@Test
	@WithMockUser(username = "admin@magalu.com.br", roles = {"ADMIN"})
	public void cadastrarLoja() throws Exception {
		Loja loja = obterDadosLoja();
		BDDMockito.given(this.lojaService.buscaPorCodigo(Mockito.anyString())).willReturn(Optional.empty());
		BDDMockito.given(this.lojaService.persistir(Mockito.any(Loja.class))).willReturn(loja);

		mvc.perform(MockMvcRequestBuilders.post(APIPATH)
				.content(this.obterJsonRequisicaoPost(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.cep").value(CEP))
				.andExpect(jsonPath("$.data.descricao").value(DESCRICAO))
				.andExpect(jsonPath("$.data.codigo").value(CODIGO))
				.andExpect(jsonPath("$.errors").isEmpty());;
	}
	
	@Test
	@WithMockUser(username = "admin@magalu.com.br", roles = {"ADMIN"})
	public void cadastrarLojaCepInvalido() throws Exception {
		Loja loja = obterDadosLoja();	
		BDDMockito.given(this.lojaService.buscaPorCodigo(Mockito.anyString())).willReturn(Optional.empty());
		BDDMockito.given(this.lojaService.persistir(Mockito.any(Loja.class))).willReturn(loja);

		mvc.perform(MockMvcRequestBuilders.post(APIPATH)
				.content(this.obterJsonRequisicaoPost(Optional.of("55554")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("CEP deve estar no formato 99999-999"));
	}
	
	@Test
	@WithMockUser
	public void buscarLoja() throws Exception {
		Loja loja = obterDadosLoja();	
		BDDMockito.given(this.lojaService.buscaPorCodigo(Mockito.anyString())).willReturn(Optional.of(loja));

		mvc.perform(MockMvcRequestBuilders.get(APIPATH + "/" + CODIGO)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.cep").value(CEP))
				.andExpect(jsonPath("$.data.descricao").value(DESCRICAO))
				.andExpect(jsonPath("$.data.codigo").value(CODIGO))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	private String obterJsonRequisicaoPost(Optional<String> cepErr) throws JsonProcessingException {
		LojaDto lojaDto = new LojaDto();
		lojaDto.setId(null);
		lojaDto.setCep(Objects.isNull(cepErr) ? CEP : cepErr.get());
		lojaDto.setCodigo(CODIGO);
        lojaDto.setDescricao(DESCRICAO);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(lojaDto);
	}
	
	private Loja obterDadosLoja() {
		Loja loja = new Loja();
		loja.setId(32L);
		loja.setCep(CEP);
		loja.setCodigo(CODIGO);
		loja.setDescricao(DESCRICAO);
		return loja;
	}

}
