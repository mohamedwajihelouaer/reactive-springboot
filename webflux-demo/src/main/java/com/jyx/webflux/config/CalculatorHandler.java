package com.jyx.webflux.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
public class CalculatorHandler {


    public Mono<ServerResponse> additionHandler(ServerRequest request) {
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a + b));
    }

    public Mono<ServerResponse> substractionHandler(ServerRequest request) {
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a - b));
    }

    public Mono<ServerResponse> multiplicationHandler(ServerRequest request) {
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a * b));
    }


    public Mono<ServerResponse> divisionHandler(ServerRequest request) {
        return process(request, (a, b) ->
                b != 0 ? ServerResponse.ok().bodyValue(a / b) :
                        ServerResponse.badRequest().bodyValue("Division by zero not allowed")
        );
    }


    private Mono<ServerResponse> process(ServerRequest request,
                                         BiFunction<Integer, Integer, Mono<ServerResponse>> opLogic) {
        int operand1 = getValue(request, "a");
        int operand2 = getValue(request, "b");
        return opLogic.apply(operand1, operand2);

    }

    /* helper method */
    private Integer getValue(ServerRequest request, String key) {
        return Integer.parseInt(request.pathVariable(key));
    }
}
