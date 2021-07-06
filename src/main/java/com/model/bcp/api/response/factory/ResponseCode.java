package com.model.bcp.api.response.factory;


public enum ResponseCode {

	OK("OK", "Peticion exitosa"),
	ERROR("ERROR", "Se produjo una excepcion");

	private String code;
	private String message;

	private ResponseCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
