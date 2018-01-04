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

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "razao_social", nullable = false)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String razaoSocial) {
		this.descricao = razaoSocial;
	}

	@Column(name = "cnpj", nullable = false)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String cnpj) {
		this.codigo = cnpj;
	}

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
