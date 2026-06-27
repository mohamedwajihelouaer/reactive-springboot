package com.jyx.webflux.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jyx.webflux.dto.MultiplyRequestDto;
import com.jyx.webflux.dto.Response;
import com.jyx.webflux.exceptions.InputValidationException;
import com.jyx.webflux.service.ReactiveCalcService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive/api/v1")
public class ReactiveMathController {

	private final ReactiveCalcService calcService;

	public ReactiveMathController(ReactiveCalcService calcService) {
		this.calcService = calcService;
	}

	@GetMapping("/square/{input}")
	public Mono<Response> findSquare(@PathVariable Integer input) {
		return this.calcService.getSquare(input);
	}

	@GetMapping("/square/{input}/mono-filter")
	public Mono<ResponseEntity<Response>> findSquareWithFilter(@PathVariable Integer input) {
		return Mono.just(input)
				.filter(i -> isBetween(i, 10, 20))
				.flatMap(i -> this.calcService.getSquare(i))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest()
				.build());
	}

	@GetMapping("/square/{input}/validation")
	public Mono<Response> findSquareWithMonoError(@PathVariable Integer input) {
		return Mono.just(input).handle((integer, responseSynchronousSink) -> {
			if (isBetween(input, 10, 20))
				responseSynchronousSink.next(integer);
			else
				responseSynchronousSink.error(new InputValidationException(integer));

		}).cast(Integer.class).flatMap(v -> this.calcService.getSquare(v));
	}

	@GetMapping("/square/{input}/mono-error")
	public Mono<Response> findSquareWithValidation(@PathVariable Integer input) {

		if (!isBetween(input, 10, 20))
			throw new InputValidationException(input);

		return this.calcService.getSquare(input);
	}

	@GetMapping("/multable/{input}")
	public Flux<Response> getMultiplicationTable(@PathVariable Integer input) {
		return this.calcService.getMultable(input);
	}

	@GetMapping(value = "/multable/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Response> getMultiplicationTableStream(@PathVariable Integer input) {
		return this.calcService.getMultable(input);
	}

	@PostMapping("mult")
	public Mono<Response> mult(@RequestBody Mono<MultiplyRequestDto> dto, @RequestHeader Map<String, String> headers) {
		System.out.println(headers);
		return this.calcService.mult(dto);
	}

	private boolean isBetween(Integer var, Integer low, Integer up) {
		return var >= low && var <= up;
	}

}
