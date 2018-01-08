package com.magalu.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

	private static final String apiPath = "/api/loja";

	@Test
	@WithMockUser(username = "admin@magalu.com.br", roles = {"ADMIN"})
	public void testCadastrarLoja() throws Exception {
		Loja loja = obterDadosLoja();
		BDDMockito.given(this.lojaService.persistir(Mockito.any(Loja.class))).willReturn(loja);

		mvc.perform(MockMvcRequestBuilders.post(apiPath)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		LojaDto lojaDto = new LojaDto();
		lojaDto.setId(null);
		lojaDto.setCep("01102-000");
		lojaDto.setCodigo("123456");
        lojaDto.setDescricao("Loja Teste");

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(lojaDto);
	}

	private Loja obterDadosLoja() {
		Loja loja = new Loja();
		loja.setCep("01102-000");
		loja.setCodigo("123456");
		loja.setDescricao("Loja Teste");
		return loja;
	}

}
