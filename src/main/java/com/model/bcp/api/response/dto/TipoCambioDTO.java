package com.model.bcp.api.response.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TipoCambioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private BigDecimal tasa;
	private String monedaOrigen;
	private String monedaDestino;

	
	public TipoCambioDTO() {}
			
	public TipoCambioDTO(Long id, BigDecimal tasa, String monedaOrigen, String monedaDestino) {
		this.id = id;
		this.tasa = tasa;
		this.monedaOrigen = monedaOrigen;
		this.monedaDestino = monedaDestino;
	}
	
	public TipoCambioDTO(BigDecimal tasa, String monedaOrigen, String monedaDestino) {
		this.tasa = tasa;
		this.monedaOrigen = monedaOrigen;
		this.monedaDestino = monedaDestino;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public BigDecimal getTasa() {
		return tasa;
	}

	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}

	public String getMonedaOrigen() {
		return monedaOrigen;
	}
	public void setMonedaOrigen(String monedaOrigen) {
		this.monedaOrigen = monedaOrigen;
	}
	public String getMonedaDestino() {
		return monedaDestino;
	}
	public void setMonedaDestino(String monedaDestino) {
		this.monedaDestino = monedaDestino;
	}

	
}
