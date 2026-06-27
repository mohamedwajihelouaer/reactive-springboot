package com.jyx.webflux.config;

import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.jyx.webflux.dto.InputFailedValidationResponse;
import com.jyx.webflux.exceptions.InputValidationException;

import reactor.core.publisher.Mono;

/**
 * configuration functional End points
 */

@Configuration
public class RouterConfig {

	@Autowired
	private RequestHandler requestHandler;

	/**
	 * avoid duplicating base URLs like top level RequestMapping
	 * 
	 * @return
	 */

	@Bean
	public RouterFunction<ServerResponse> baseUrlRouter() {
		return RouterFunctions.route().path("api/v1/router", this::serverRouterFunction).build();
	}

	// @Bean with path based router no longer needs to be public @Bean
	public RouterFunction<ServerResponse> serverRouterFunction() {
		return RouterFunctions.route()
				// .GET("square/{input}", requestHandler::squareHandler)
				.GET("square/{input}", RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")), requestHandler::squareHandler) // request predicate with pattern matching
				.GET("square/{input}", req -> ServerResponse.badRequest().bodyValue("Only 10 to 20 are allowed")) // fallback in case previous did not hit
				
				.GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
					.onError(InputValidationException.class, exceptionHandler())
				
				.GET("table/{input}", requestHandler::tableHandler)
				.GET("table/{input}/stream", requestHandler::tableStreamHandler)
				
				.POST("multiply", requestHandler::multiplyHandler) // check header
				
				.build();
	}

	private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
		return (err, req) -> {
			InputValidationException ex = (InputValidationException) err;
			InputFailedValidationResponse response = new InputFailedValidationResponse();
			response.setInput(ex.getInput());
			response.setErrCode(ex.getErrCode());
			response.setMessage(ex.getMessage());
			return ServerResponse.badRequest().bodyValue(response);
		};

	}

}
