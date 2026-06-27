package com.jyx.webflux.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.jyx.webflux.dto.Response;

@Service
public class MathService {

	public Response findSquare(Integer input) {
		return new Response(input * input);
	}

	public List<Response> getMultiplicationTable(Integer input) {
		return IntStream.rangeClosed(1, 10)
				.peek(i -> DelayUtil.delay(1))
				.peek( i-> System.out.print("Math Service Processing " + i))
				.mapToObj(i -> new Response(i * input))
				.collect(Collectors.toList());
	}
}
