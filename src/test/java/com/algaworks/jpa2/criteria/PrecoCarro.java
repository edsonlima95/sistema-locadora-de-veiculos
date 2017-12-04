package com.algaworks.jpa2.criteria;

import java.math.BigDecimal;

public class PrecoCarro {
	
	private String placa;
	private BigDecimal valorDiaria;
	
	
	public PrecoCarro(String placa, BigDecimal valorDiaria) {
		super();
		this.placa = placa;
		this.valorDiaria = valorDiaria;
	}


	public String getPlaca() {
		return placa;
	}


	public BigDecimal getValorDiaria() {
		return valorDiaria;
	}
	
	
}
