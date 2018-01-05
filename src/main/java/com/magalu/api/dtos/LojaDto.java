package com.magalu.api.dtos;

public class LojaDto {

	private Long id;
	private String codigo;
	private String descricao;
	private String cep;

	public LojaDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "LojaDto [id=" + id + ", codigo=" + codigo + ", descricao=" + descricao + ", cep=" + cep + "]";
	}

}
