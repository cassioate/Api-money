package com.tessaro.moneyapi.exceptionhandler;

public class Erro {

	private String mensagemUsuario;
	private String mensagemDesenvolvedor;
	
	public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
		super();
		this.mensagemUsuario = mensagemUsuario;
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
	}
	
	public String getMensagemUsuario() {
		return mensagemUsuario;
	}
	public String getMensagemDesenvolvedor() {
		return mensagemDesenvolvedor;
	}
	
}
