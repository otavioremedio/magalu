package com.magalu.api.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.magalu.api.entities.Conta;
import com.magalu.api.enums.PerfilEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ContaRepositoryTest {

	private String email = "admin@magalu.com.br";

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Before
	public void setUp() throws Exception {
		Conta c = new Conta();
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		c.setEmail("admin@magalu.com.br");
		c.setNome("Admin");
		c.setPerfil(PerfilEnum.ROLE_ADMIN);
		c.setSenha(bCryptEncoder.encode("123"));
		this.contaRepository.save(c);
	}

	@After
    public final void tearDown() {
		this.contaRepository.deleteAll();
	}

	@Test
	public void findByEmail() {
		Conta c = this.contaRepository.findByEmail("admin@magalu.com.br");

		assertEquals(email, c.getEmail());
	}

	@Test
	public void logarComContaValida() {
		Conta c = this.contaRepository.findByEmail("admin@magalu.com.br");
		Authentication a = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				c.getEmail(), "123"));

		assertTrue(a != null);
	}

	@Test
	public void logarComContaInvalida() {
		Conta c = this.contaRepository.findByEmail("admin@magalu.com.br");
		try{
			Authentication a = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					c.getEmail(), "12345"));
			fail();
		}catch(Exception e){
			assertThat(e.getMessage(), is ("Bad credentials"));
		}



	}


}
