package com.jyx.webflux.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.jyx.webflux.dto.MultiplyRequestDto;
import com.jyx.webflux.dto.Response;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveCalcService {

	public Mono<Response> getSquare(Integer input) {
		return Mono.fromSupplier(() -> input * input).map(Response::new);
	}

	public Flux<Response> getMultable(Integer input) {
		return Flux.range(1, 10).delayElements(Duration.ofSeconds(1))
				// blocking sleep
				// .doOnNext(i -> DelayUtil.delay(1))
				.doOnNext(i -> System.out.println("Reactive Processing: " + i)).map(i -> new Response(i * input));
	}

	public Mono<Response> mult(Mono<MultiplyRequestDto> dto) {
		return dto.map(o -> o.first() * o.second()).map(Response::new);
	}
}
