package com.model.bcp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.model.bcp.api.response.dto.ConsultaCambioDTO;
import com.model.bcp.domain.TipoCambio;

import rx.Completable;
import rx.Single;

public interface TipoCambioService {

	Optional<TipoCambio> findById(Long id);
	List<TipoCambio> findAll();
	TipoCambio save(TipoCambio tipoCambio) ;
	void delete(Long id);
	Single<TipoCambio> saveRx(TipoCambio tipoCambio) ;
	Single<TipoCambio> updateRx(TipoCambio tipoCambio);
	Single<BigDecimal> calculate(ConsultaCambioDTO consultaCambio);
	Single<List<TipoCambio>> findAllRx();
	Completable deleteRx(Long id);
	List<TipoCambio> saveList(List<TipoCambio> listTpoCambio);
}
