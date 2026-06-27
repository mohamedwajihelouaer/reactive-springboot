package com.jyx.webflux.config;


import com.jyx.webflux.exceptions.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class CalculatorRouter {

    @Autowired
    private CalculatorHandler calculatorHandler;

    @Bean
    public RouterFunction<ServerResponse> baseCalculatorUrlRouter() {
        return RouterFunctions.route().path("api/v1/calculator", this::serverRouterFunction).build();
    }

    public RouterFunction<ServerResponse> serverRouterFunction() {
        return RouterFunctions.route()
                .GET("{a}/{b}", isOperation("+"), calculatorHandler::additionHandler) // request predicate with pattern matching
                .GET("{a}/{b}", isOperation("-"), calculatorHandler::substractionHandler) // request predicate with pattern matching
                .GET("{a}/{b}", isOperation("*"), calculatorHandler::multiplicationHandler) // request predicate with pattern matching
                .GET("{a}/{b}", isOperation("/"), calculatorHandler::divisionHandler) // request predicate with pattern matching
                .GET("{a}/{b}", req -> ServerResponse.badRequest().bodyValue("Illegal Operator"))
                .build();
    }


    private RequestPredicate isOperation(String operation) {
        return RequestPredicates.headers(h -> operation.equals(h.asHttpHeaders().toSingleValueMap().get("OP")));
    }
}
