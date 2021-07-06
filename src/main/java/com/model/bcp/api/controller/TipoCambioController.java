package com.model.bcp.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.bcp.api.response.ConsultaCambioResponse;
import com.model.bcp.api.response.TipoCambioListResponse;
import com.model.bcp.api.response.TipoCambioResponse;
import com.model.bcp.api.response.dto.ConsultaCambioDTO;
import com.model.bcp.api.response.dto.TipoCambioDTO;
import com.model.bcp.api.response.factory.HeaderResponse;
import com.model.bcp.api.response.factory.ResponseCode;
import com.model.bcp.domain.TipoCambio;
import com.model.bcp.service.TipoCambioService;

import io.swagger.annotations.ApiOperation;
import rx.Single;
import rx.schedulers.Schedulers;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST})
@RequestMapping(value="/tipoCambio")
public class TipoCambioController {
	
	@Autowired
	private TipoCambioService tipoCambioService;

	
	@ApiOperation(value = "Dar de Alta: Lista de Tipo de Cambio", notes = "Guardar parametros necesarios de configuracion de la tasa de cambio...")
	@PostMapping("/")
	public @ResponseBody TipoCambioListResponse save(@RequestBody List<TipoCambioDTO> listTipoCambioDTO) {
		TipoCambioListResponse response = new TipoCambioListResponse();
    	try{
    		
    		List<TipoCambio> listTipoCambio = toEntityList(listTipoCambioDTO);
    		tipoCambioService.saveList(listTipoCambio);
        	response.setBody(toDTOList(listTipoCambio));
        	response.setHeader(new HeaderResponse(ResponseCode.OK));
        }catch(Exception e){
        	response.setHeader(new HeaderResponse(ResponseCode.ERROR));
        	System.out.println("Error: "+e.getMessage());
        }
        return response;
	}
	
	@ApiOperation(value = "Modificacion: Tipo de Cambio", notes = "Modificar parametros necesarios de configuracion de la tasa de cambio ya ingresados")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT )
	public @ResponseBody TipoCambioResponse update(@RequestBody TipoCambioDTO tipoCambioDTO, @PathVariable("id") Long id) {
		TipoCambioResponse response = new TipoCambioResponse();
    	try{
    		TipoCambio tipoCambio = toEntity(tipoCambioDTO);
    		tipoCambio.setId(id);
    		tipoCambioService.save(tipoCambio);
        	response.setBody(toDTO(tipoCambio));
        	response.setHeader(new HeaderResponse(ResponseCode.OK));
        }catch(Exception e){
        	response.setHeader(new HeaderResponse(ResponseCode.ERROR));
        	System.out.println("Error: "+e.getMessage());
        }
    	return response;
	}
	
	@ApiOperation(value = "Eliminacion: Tipo de Cambio", notes = "Eliminar parametros necesarios de configuracion de la tasa de cambio ya ingresados")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE )
	public @ResponseBody TipoCambioResponse delete(@PathVariable("id") Long id) {
		TipoCambioResponse response = new TipoCambioResponse();
    	try{
    		tipoCambioService.delete(id);
        	response.setHeader(new HeaderResponse(ResponseCode.OK));
        }catch(Exception e){
        	response.setHeader(new HeaderResponse(ResponseCode.ERROR));
        	System.out.println("Error: "+e.getMessage());
        }
    	return response;
    	
	}
	
	@ApiOperation(value = "Buscar por Identificador: Tipo de Cambio", notes = "Buscar un parametro de configuracion de la tasa de cambio ya ingresado")
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<TipoCambioDTO> findById(@PathVariable("id") Long id) {
		Optional<TipoCambio> rspTipoCambio =tipoCambioService.findById(id); 
		return rspTipoCambio.isPresent()?ResponseEntity.ok(toDTO(rspTipoCambio.get())):ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Buscar Todos: Tipo de Cambio", notes = "Buscar todos los parametros de configuracion de la tasa de cambio ya ingresado")
	@RequestMapping(value="/findAll", method = RequestMethod.GET)
	public @ResponseBody List<TipoCambioDTO> findAll() {
		return toDTOList(tipoCambioService.findAll());
	}
	
	
	/****  USING RXJAVA ****/
	@ApiOperation(value = "Dar de Alta [Programacion Reactiva]: Tipo de Cambio", notes = "Guardar parametros necesarios de configuracion de la tasa de cambio")
	@RequestMapping(value = "/rx/save", method = RequestMethod.POST )
    public Single<TipoCambioDTO> rxSave(@RequestBody TipoCambio tipoCambio) {
		return tipoCambioService.saveRx(tipoCambio)
                .subscribeOn(Schedulers.io())
                .map(this::toDTO);
    }

	@ApiOperation(value = "Modificacion [Programacion Reactiva]: Tipo de Cambio", notes = "Modificar parametros necesarios de configuracion de la tasa de cambio ya ingresados")
	@RequestMapping(value = "/rx/update", method = RequestMethod.PUT )
    public Single<TipoCambio> rxUpdate(@RequestBody TipoCambio tipoCambio) {
		return tipoCambioService.updateRx(tipoCambio)
                .subscribeOn(Schedulers.io());
    }
	
	@ApiOperation(value = "Buscar todos [Programacion Reactiva]: Tipo de Cambio", notes = "Buscar todos los parametros de configuracion de la tasa de cambio ya ingresado")
	@RequestMapping(value="/findAllRx", method = RequestMethod.GET)
	public @ResponseBody Single<List<TipoCambioDTO>> findAllRx()  {
		return tipoCambioService.findAllRx()
				.subscribeOn(Schedulers.io())
				.map(this::toDTOList);
	}
	
	@ApiOperation(value = "Calculo de aplicar un tipo de cambio a un monto [Resultado del Ejercicio]", notes = "Este es el resultado de lo que se pide en la evaluacion BCP")
	@RequestMapping(value = "/rx/calculate", method = RequestMethod.POST )
    public Single<ConsultaCambioResponse> calculate(@RequestBody ConsultaCambioDTO consultaCambioDTO) {
		 return tipoCambioService.calculate(consultaCambioDTO)
                .subscribeOn(Schedulers.io())
                .map(this::toResult);
		
    }
	
	@ApiOperation(value = "Eliminacion [Programacion Reactiva]: Tipo de Cambio", notes = "Eliminar parametros necesarios de configuracion de la tasa de cambio ya ingresados")
    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity> deleteRx(@PathVariable("id") Long id) {
        return tipoCambioService.deleteRx(id)
                .subscribeOn(Schedulers.io()).toSingle( () -> ResponseEntity.ok().build());
	}
	
	
	
	/******** Utils ********/
	private TipoCambio toEntity(TipoCambioDTO tipoCambioDTO) {
		TipoCambio entity = new TipoCambio();
        BeanUtils.copyProperties(tipoCambioDTO, entity);
        return entity;
    }
	
	private TipoCambioDTO toDTO(TipoCambio tipoCambio) {
		TipoCambioDTO dto = new TipoCambioDTO();
        BeanUtils.copyProperties(tipoCambio, dto);
        return dto;
    }
	
	 private List<TipoCambioDTO> toDTOList(List<TipoCambio> tipoCambioList) {
        return tipoCambioList
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

	 private List<TipoCambio> toEntityList(List< TipoCambioDTO> tipoCambioDTOList) {
	        return tipoCambioDTOList
	                .stream()
	                .map(this::toEntity)
	                .collect(Collectors.toList());
	    }

	 
	private ConsultaCambioResponse toResult(BigDecimal monto) {
		ConsultaCambioResponse response = new ConsultaCambioResponse();
		response.setHeader(new HeaderResponse(ResponseCode.OK));
		response.setBody(monto);
        return response;
    }
}
