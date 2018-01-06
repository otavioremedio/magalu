package com.magalu.api.dtos;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

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

	@NotEmpty(message = "Código não pode ser vazio.")
	@Length(min = 3, max = 10, message = "Código deve conter entre 3 e 10 caracteres.")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@NotEmpty(message = "Descrição não pode ser vazia.")
	@Length(min = 3, max = 150, message = "Descrição deve conter entre 3 e 150 caracteres.")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@NotEmpty(message = "CEP não pode ser vazio.")
	@Pattern(regexp = "^[0-9]{5}+[\\-]{1}+[0-9{3}]+$", message="CEP deve estar no formato 99999-999")
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
