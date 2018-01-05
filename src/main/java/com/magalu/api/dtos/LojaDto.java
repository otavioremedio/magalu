package com.magalu.api.dtos;

public class LojaDto {
	
	private Long id;
	private String codigo;

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

	@Override
	public String toString() {
		return "LojaDto [id=" + id + ", codigo=" + codigo + "]";
	}
	
}
