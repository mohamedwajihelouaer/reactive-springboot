package com.jyx.webflux.dto;

import java.time.LocalDate;
import java.util.Objects;

//import lombok.Data;

//@Data
public record Response(LocalDate date, Integer output) {
	
	public Response {
		Objects.nonNull(date);
		Objects.nonNull(output);
		
	}
	
	public Response(Integer output) {
		this(LocalDate.now(), output);
	}
	
	 

}
