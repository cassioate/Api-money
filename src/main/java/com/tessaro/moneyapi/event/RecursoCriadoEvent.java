package com.tessaro.moneyapi.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class RecursoCriadoEvent extends ApplicationEvent {
	private static final long serialVersionUID = -7528995619288487878L;
	
	private HttpServletResponse response;
	private Long codigo;
	
	public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo) {
		super(source);
		this.response = response;
		this.codigo = codigo;
	}

}
