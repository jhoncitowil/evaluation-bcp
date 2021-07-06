package com.model.bcp.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.bcp.domain.TipoCambio;

public interface TipoCambioRepository extends JpaRepository<TipoCambio, Long> {
	
	@Query(value = "SELECT u FROM TipoCambio u WHERE u.monedaOrigen = :monedaOrigen and u.monedaDestino = :monedaDestino")
	TipoCambio findByOrigenDestino(@Param(value="monedaOrigen") String monedaOrigen, @Param(value="monedaDestino") String monedaDestino);

	
	
}
