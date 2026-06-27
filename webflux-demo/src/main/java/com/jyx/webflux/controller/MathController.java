package com.jyx.webflux.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jyx.webflux.dto.Response;
import com.jyx.webflux.service.MathService;

@RestController
@RequestMapping("/api/v1/math")
public class MathController {

	private final MathService mathService;

	public MathController(MathService mathService) {
		this.mathService = mathService;
	}

	@GetMapping("/square/{input}")
	public Response findSquare(@PathVariable Integer input) {
		return this.mathService.findSquare(input);
	}
	
	
	@GetMapping("/multable/{input}")
	public List<Response> getMultiplicationTable(@PathVariable Integer input) {
		return this.mathService.getMultiplicationTable(input);
	}
	
	
}
