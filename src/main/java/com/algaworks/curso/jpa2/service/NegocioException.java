package com.algaworks.curso.jpa2.service;

public class NegocioException extends Exception {

	//Passa a mensagem para a classe exception.
	public NegocioException(String mensagem) {
		super(mensagem);
	}
	
}
