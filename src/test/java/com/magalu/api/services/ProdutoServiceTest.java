package com.magalu.api.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
import com.magalu.api.entities.Produto;
import com.magalu.api.repositories.LojaRepository;
import com.magalu.api.repositories.ProdutoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProdutoServiceTest {

	@MockBean
	private ProdutoRepository produtoRepository;

	@MockBean
	private LojaRepository lojaRepository;

	@MockBean
	private LojaService lojaService;

	@Autowired
	private ProdutoService	produtoService;

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.produtoRepository.findByCodigo(Mockito.anyString())).willReturn(new Produto());
		BDDMockito.given(this.produtoRepository.save(Mockito.any(Produto.class))).willReturn(new Produto());
		BDDMockito.given(this.lojaRepository.findByCodigo(Mockito.anyString())).willReturn(new Loja());
		BDDMockito.given(this.lojaRepository.save(Mockito.any(Loja.class))).willReturn(new Loja());
		BDDMockito.given(this.lojaService.buscaPorCodigo(Mockito.anyString())).willReturn(Optional.ofNullable(new Loja()));
	}

	@Test
	public void testPersistirProduto() {
		Produto produto = this.produtoService.persistir(new Produto());

		assertNotNull(produto);
	}

	@Test
	public void buscaPorCodigo() {
		Optional<Produto> produto = this.produtoService.buscaPorCodigo(Mockito.anyString());
		assertTrue(produto.isPresent());
	}

	@Test
	public void buscaDistancia() {
		String origem = "01102-000";
		String destino = "02010-000";
		String distancia;

		distancia = this.produtoService.buscaDistancia(origem, destino);

		assertEquals("2,10 km", distancia);
	}

	@Test
	public void buscaPorDescricao() {
		Optional<Produto> produto = this.produtoService.buscaPorCodigo("1");

		if(produto.isPresent()){
			Produto p = produto.get();
			p.setLojas(new ArrayList<Loja>());
			p.getLojas().add(this.lojaService.buscaPorCodigo("1").get());
		}


		assertTrue(produto.isPresent());
	}

}
