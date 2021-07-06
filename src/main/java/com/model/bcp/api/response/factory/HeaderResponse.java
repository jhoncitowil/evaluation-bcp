package com.model.bcp.api.response.factory;

public class HeaderResponse {

	private String code;
	private String message;
	
	public HeaderResponse() {
	}

	public HeaderResponse(ResponseCode responseCode) {
		this.code = responseCode.getCode();
		this.message = responseCode.getMessage();
	}

	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
