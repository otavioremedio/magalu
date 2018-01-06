package com.magalu.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.magalu.api.entities.Loja;
import com.magalu.api.repositories.LojaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LojaServiceTest {

	@MockBean
	private LojaRepository lojaRepository;

	@Autowired
	private LojaService lojaService;

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.lojaRepository.findByCodigo(Mockito.anyString())).willReturn(new Loja());
		BDDMockito.given(this.lojaRepository.save(Mockito.any(Loja.class))).willReturn(new Loja());
	}

	@Test
	public void testBuscarLojaPorCodigo() {
		Optional<Loja> loja = this.lojaService.buscaPorCodigo(Mockito.anyString());

		assertTrue(loja.isPresent());
	}
	
	@Test
	public void testPersistirLoja() {
		Loja loja = this.lojaService.persistir(new Loja());

		assertNotNull(loja);
	}

}
