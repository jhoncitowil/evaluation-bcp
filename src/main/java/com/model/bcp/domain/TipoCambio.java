package com.model.bcp.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class TipoCambio {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	private BigDecimal tasa;
	private String monedaOrigen;
	private String monedaDestino;

	
	public TipoCambio() {}
			
	public TipoCambio(Long id, BigDecimal tasa, String monedaOrigen, String monedaDestino) {
		super();
		this.id = id;
		this.tasa = tasa;
		this.monedaOrigen = monedaOrigen;
		this.monedaDestino = monedaDestino;
	}
	
	public TipoCambio(BigDecimal tasa, String monedaOrigen, String monedaDestino) {
		super();
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
