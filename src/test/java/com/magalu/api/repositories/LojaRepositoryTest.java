package com.magalu.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.magalu.api.entities.Loja;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LojaRepositoryTest {

	@Autowired
	private LojaRepository lojaRepository;

	private static final String codigo = "1";

	@Before
	public void setUp() throws Exception {
		Loja loja = new Loja();
		loja.setDescricao("Magazine Luiza 1");
		loja.setCodigo(codigo);
		loja.setCep("01102000");
		this.lojaRepository.save(loja);
	}

	@After
    public final void tearDown() {
		this.lojaRepository.deleteAll();
	}

	@Test
	public void testBuscarPorCnpj() {
		Loja loja = this.lojaRepository.findByCodigo(codigo);

		assertEquals(codigo, loja.getCodigo());
	}

}
