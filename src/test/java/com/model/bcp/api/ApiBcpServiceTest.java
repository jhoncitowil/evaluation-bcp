package com.model.bcp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.FluxExchangeResult;

import com.model.bcp.api.repository.TipoCambioRepository;
import com.model.bcp.domain.TipoCambio;
import com.model.bcp.service.TipoCambioService;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class ApiBcpServiceTest {

	@Autowired
	private TipoCambioService tipoCambioService;
	
	@MockBean
	private TipoCambioRepository tipoCambioRepository;
	
	
//	@Mock
//	private List<String> list;
//	
//	@Test
//	public void findAllTest() {
//		Flux<String> stringFlux = Flux.just("A", "B", "C")
//				.log();
//		
//		StepVerifier.create(stringFlux)
//				.expectNext("A")
//				.expectNext("B")
//				.expectNext("C")
//				.verifyComplete();
//	}
	
	@Test
	public void findAllTest() {
		when(tipoCambioRepository.findAll()).thenReturn(
				Stream.of(new TipoCambio(1L, BigDecimal.TEN, "PEN", "USD"),
						new TipoCambio(2L, BigDecimal.ONE, "USD", "PEN")).collect(Collectors.toList()));
		assertEquals(2, tipoCambioService.findAll().size());
	}
	
	@Test
	public void findByIdTest() {
		TipoCambio tipoCambio = new TipoCambio(1L, BigDecimal.TEN, "PEN", "USD");
		when(tipoCambioRepository.findById(1L)).thenReturn(Optional.of(tipoCambio));
		assertEquals(tipoCambio, tipoCambioService.findById(1L).get());
	}
	
	@Test
	public void saveTest() {
		TipoCambio tipoCambio = new TipoCambio(1L, BigDecimal.TEN, "PEN", "USD");
		when(tipoCambioRepository.save(tipoCambio)).thenReturn(tipoCambio);
		assertEquals(tipoCambio, tipoCambioService.save(tipoCambio));
	}
	
	@Test
	public void deleteTest() {
		Long id = 1L;
		tipoCambioService.delete(id);
		verify(tipoCambioRepository, times(1)).deleteById(id);

	}
}
