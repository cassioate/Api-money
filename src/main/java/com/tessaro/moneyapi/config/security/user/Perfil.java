package com.tessaro.moneyapi.config.security.user;

import lombok.Getter;

@Getter
public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),
	USUARIO(2, "ROLE_USER"),
	GUEST(3, "ROLE_GUEST");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public static Perfil toEnum (Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Perfil x : Perfil.values()) {
			if(cod.equals(x.getCod())){
				return x;
			}
		}
		throw new IllegalArgumentException("id invalido: "+ cod);
	}

}
