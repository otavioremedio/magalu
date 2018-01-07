package com.magalu.api.services;

import static org.junit.Assert.assertEquals;
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

import com.magalu.api.entities.Produto;
import com.magalu.api.repositories.ProdutoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProdutoServiceTest {

	@MockBean
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoService	produtoService;

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.produtoRepository.findByCodigo(Mockito.anyString())).willReturn(new Produto());
		BDDMockito.given(this.produtoRepository.save(Mockito.any(Produto.class))).willReturn(new Produto());
	}

	@Test
	public void testPersistirProduto() {
		Produto produto = this.produtoService.persistir(new Produto());

		assertNotNull(produto);
	}
	
	@Test
	public void testBuscaProdutoPorCodigo() {
		Optional<Produto> produto = this.produtoService.buscaPorCodigo(Mockito.anyString());
		assertTrue(produto.isPresent());
	}
	
	@Test
	public void testBuscaDistancia() {
		String origem = "01102-000";
		String destino = "02010-000";
		String distancia;
		
		distancia = this.produtoService.buscaDistancia(origem, destino);
		
		assertEquals("2,10 km", distancia);
	}

}
