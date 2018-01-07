package com.magalu.api.dtos;

import java.util.ArrayList;
import java.util.List;

public class BuscaDto {
	
	private List<LojaDto> lojas;
	private String valor;
	
	public BuscaDto() {
		this.setLojas(new ArrayList<LojaDto>());		
	}
	
	public List<LojaDto> getLojas() {
		return lojas;
	}
	public void setLojas(List<LojaDto> lojas) {
		this.lojas = lojas;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return "BuscaDto [lojas=" + lojas + ", valor=" + valor + "]";
	}
	
	
}
