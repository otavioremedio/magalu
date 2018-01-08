package com.magalu.api.repositories;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.After;
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
import com.magalu.api.entities.Produto;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProdutoRepositoryTest {

	@Autowired
	private ProdutoRepository produtoRepository;

	@MockBean
	private LojaRepository lojaRepository;


	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.lojaRepository.findByCodigo(Mockito.anyString())).willReturn(new Loja("Loja 1","123","01102-000"));
	}

	@After
    public final void tearDown() {
		this.produtoRepository.deleteAll();
	}

	@Test
	public void testBuscarPorCodigo() {

		Produto p = new Produto();
		p.setCodigo("123456");
		p.setDescricao("Celular");
		p.setValor(new BigDecimal(30.50));
		p.setLojas(new ArrayList<Loja>());
		p.getLojas().add(this.lojaRepository.findByCodigo("123"));
		this.produtoRepository.save(p);
		p = this.produtoRepository.findByCodigo("123456");

		assertTrue(p != null);
	}

}
