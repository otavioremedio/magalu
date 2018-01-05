package com.magalu.api.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {

   
    private Long id;
    private String codigo;
    private String descricao;
    private BigDecimal valor;
    private List<Loja> lojas;

   
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="produto_id")
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
	@Column(name="codigo", nullable = false)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(name="descricao", nullable = false)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name="valor", nullable = false)
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Loja> getLojas() {
		return lojas;
	}

	public void setLojas(List<Loja> lojas) {
		this.lojas = lojas;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", codigo=" + codigo + ", descricao=" + descricao + ", valor=" + valor + ", lojas="
				+ lojas + "]";
	}

}
