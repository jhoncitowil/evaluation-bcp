package com.model.bcp.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.bcp.api.repository.TipoCambioRepository;
import com.model.bcp.api.response.dto.ConsultaCambioDTO;
import com.model.bcp.domain.TipoCambio;
import com.model.bcp.service.TipoCambioService;

import rx.Completable;
import rx.Single;

@Service("tipoCambioService")
public class TipoCambioServiceImpl implements TipoCambioService {
	
	@Autowired
	private TipoCambioRepository tipoCambioRepository;
	
	@Override
	public Optional<TipoCambio> findById(Long id) {
		return tipoCambioRepository.findById(id);
	}
	
	@Override
	public List<TipoCambio> findAll() {
		return tipoCambioRepository.findAll();
	}
	
	@Override
	@Transactional()
	public TipoCambio save(TipoCambio tipoCambio) {
		return tipoCambioRepository.save(tipoCambio);
	}
	
	@Override
	@Transactional()
	public List<TipoCambio> saveList(List<TipoCambio> listTpoCambio) {
		listTpoCambio.forEach(e->{
			TipoCambio t = tipoCambioRepository.findByOrigenDestino(e.getMonedaOrigen(), e.getMonedaDestino());
			if(t!=null) {
				e.setId(t.getId());
			}
		});
		return tipoCambioRepository.saveAll(listTpoCambio);
	}
	
	@Override
	@Transactional()
	public void delete(Long id) {
		tipoCambioRepository.deleteById(id);
	}
	
	
	@Override
	@Transactional()
	public Single<TipoCambio> updateRx(TipoCambio tipoCambio) {
		return Single.create(singleSubscriber -> {
	        Optional<TipoCambio> optionalTC = tipoCambioRepository.findById(tipoCambio.getId());
	        if (!optionalTC.isPresent())
	            singleSubscriber.onError(new EntityNotFoundException());
	        else {
	            tipoCambioRepository.save(tipoCambio);
	            singleSubscriber.onSuccess(tipoCambio);
	        }
	    });
    }
	
	@Override
	@Transactional()
	public Single<TipoCambio> saveRx(TipoCambio tipoCambio) {
		//creamos flujo y nos suscribimos
		return Single.create(singleSubscriber -> {
    		try {
    			tipoCambioRepository.save(tipoCambio);
	            singleSubscriber.onSuccess(tipoCambio);
    		}catch (Exception e) {
    			singleSubscriber.onError(e);
			}
    	});
    }

	@Override
	@Transactional()
	public  Completable deleteRx(Long id) {
	        return Completable.create(completableSubscriber -> {
	            Optional<TipoCambio> optionalTC = tipoCambioRepository.findById(id);
	            if (!optionalTC.isPresent())
	                completableSubscriber.onError(new EntityNotFoundException());
	            else {
	            	tipoCambioRepository.delete(optionalTC.get());
	                completableSubscriber.onCompleted();
	            }
	        });
	    }
	 
	@Override
	public Single<BigDecimal> calculate(ConsultaCambioDTO consultaCambio) {
		return Single.create(singleSubscriber -> {
	        TipoCambio tipoCambio = tipoCambioRepository.findByOrigenDestino(consultaCambio.getMonedaOrigen(), consultaCambio.getMonedaDestino());
	        if (tipoCambio==null)
	            singleSubscriber.onError(new RuntimeException("No se encontro tasa de cambio del dia"));
	        else {
	        	BigDecimal result = tipoCambio.getTasa().multiply(consultaCambio.getMonto());
    			singleSubscriber.onSuccess(result);
	        }
	    });
    }
	
	
	@Override
    public Single<List<TipoCambio>> findAllRx() {
		 return Single.create(singleSubscriber -> {
			 	//List<TipoCambio> tipoCambios = tipoCambioRepository.findAll(PageRequest.of(page, limit)).getContent();
			 	 List<TipoCambio> tipoCambios = tipoCambioRepository.findAll();
	            singleSubscriber.onSuccess(tipoCambios);
	        });
	  }

    
}
