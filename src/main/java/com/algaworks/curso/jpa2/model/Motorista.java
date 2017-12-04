package com.algaworks.curso.jpa2.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("MOTORISTA")//Valor sera inserido no campo tipo_pessoa, da tabela pessoa.
public class Motorista extends Pessoa {

	private String numeroCNH;

	public String getNumeroCNH() {
		return numeroCNH;
	}
	public void setNumeroCNH(String numeroCNH) {
		this.numeroCNH = numeroCNH;
	}
	
	//Os metodos GETTERS e SETTERS são herdados da classe pessoa
}

