package com.magalu.api.dtos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.magalu.api.entities.Loja;

public class ProdutoDto {

   
    private Long id;
    private String codigo;
    private String descricao;
    private BigDecimal valor;
    private List<LojaDto> lojas;

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

	@NumberFormat(style = Style.NUMBER, pattern = "######,##")
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@NotEmpty(message = "Pelo menos uma loja deve ser se escolhida.")
	public List<LojaDto> getLojas() {
		return lojas;
	}

	public void setLojas(List<LojaDto> lojas) {
		this.lojas = lojas;
	}

	@Override
	public String toString() {
		return "ProdutoDto [id=" + id + ", codigo=" + codigo + ", descricao=" + descricao + ", valor=" + valor
				+ ", lojas=" + lojas + "]";
	}
	
}
