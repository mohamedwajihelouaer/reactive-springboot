package com.jyx.webflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.jyx.webflux.dto.InputFailedValidationResponse;
import com.jyx.webflux.dto.MultiplyRequestDto;
import com.jyx.webflux.dto.Response;
import com.jyx.webflux.exceptions.InputValidationException;
import com.jyx.webflux.service.ReactiveCalcService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {

	@Autowired
	private ReactiveCalcService calcService;
	
	 
	public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
		Integer input = Integer.valueOf(serverRequest.pathVariable("input"));
		Mono<Response> responseMono = this.calcService.getSquare(input);
		return ServerResponse.ok().body(responseMono, Response.class);
	}
	
	
	public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
		Integer input = Integer.valueOf(serverRequest.pathVariable("input"));
		Flux<Response> responseFlux = this.calcService.getMultable(input);
		return ServerResponse.ok().body(responseFlux, Response.class);
	}
	
	public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
		Integer input = Integer.valueOf(serverRequest.pathVariable("input"));
		Flux<Response> responseFlux = this.calcService.getMultable(input);
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(responseFlux, Response.class);
	}
	
	
	public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
		Mono<MultiplyRequestDto> requestMonoDto = serverRequest.bodyToMono(MultiplyRequestDto.class);
		Mono<Response> responseMono = this.calcService.mult(requestMonoDto);
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(responseMono, Response.class);
	}
	
	
	// Exception handling
	public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
		Integer input = Integer.valueOf(serverRequest.pathVariable("input"));
		if (input < 10 || input > 20) {
			return Mono.error(new InputValidationException(input));
		}
			  
		
		Mono<Response> responseMono = this.calcService.getSquare(input);
		return ServerResponse.ok().body(responseMono, Response.class);
	}
	
	
	
	
	
	
}
