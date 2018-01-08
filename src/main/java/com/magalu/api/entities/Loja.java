package com.magalu.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "loja")
public class Loja implements Serializable {

	private static final long serialVersionUID = 3960436649365666213L;

	private Long id;
	private String descricao;
	private String codigo;
	private String cep;

	public Loja() {
	}

	public Loja(String descricao, String codigo, String cep) {
		this.descricao = descricao;
		this.codigo = codigo;
		this.cep = cep;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="loja_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "descricao", nullable = false)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String razaoSocial) {
		this.descricao = razaoSocial;
	}

	@Column(name = "codigo", nullable = false)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String cnpj) {
		this.codigo = cnpj;
	}

	@Column(name = "cep", nullable = false)
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "Loja [id=" + id + ", descricao=" + descricao + ", codigo=" + codigo + ", cep=" + cep + "]";
	}

}
