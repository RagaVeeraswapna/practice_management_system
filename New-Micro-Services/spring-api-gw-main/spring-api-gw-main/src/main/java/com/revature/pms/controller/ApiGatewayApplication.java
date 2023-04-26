package com.revature.pms.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.pms.exception.handler.ErrorResponse;

@RestController
public class ApiGatewayApplication {
	
	@GetMapping("/G1-allergy-service-fallback")
	public ResponseEntity<ErrorResponse> allergyServiceFallback() {
		ErrorResponse errorDetails = new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, LocalDateTime.now(),
				"Allergy Service is down!");
		return new ResponseEntity<ErrorResponse>(errorDetails, HttpStatus.SERVICE_UNAVAILABLE);
	}
}
