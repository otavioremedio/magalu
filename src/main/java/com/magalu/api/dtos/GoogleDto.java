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

public class GoogleDto {

	private List<String> destination_addresses;
	private List<String> origin_addresses;
    private List<RowDto> rows;
	
	public List<String> getDestination_addresses() {
		return destination_addresses;
	}
	public void setDestination_addresses(List<String> destination_addresses) {
		this.destination_addresses = destination_addresses;
	}
	public List<String> getOrigin_addresses() {
		return origin_addresses;
	}
	public void setOrigin_addresses(List<String> origin_addresses) {
		this.origin_addresses = origin_addresses;
	}
	
	public List<RowDto> getRows() {
		return rows;
	}
	
	public void setRows(List<RowDto> rows) {
		this.rows = rows;
	}
	
	
	
	
	
    
	    
    
    	
}
