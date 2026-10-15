package com.codegnan.app.ecommercebackend.resource;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codegnan.app.ecommercebackend.catalogue.dto.CustomerResponseDto;
import com.codegnan.app.ecommercebackend.catalogue.dto.SignInRequestDto;
import com.codegnan.app.ecommercebackend.catalogue.dto.SignUpRequestDto;
import com.codegnan.app.ecommercebackend.catalogue.service.CustomerService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/rest/api/v1/auth")
public class AuthResource {

	private final CustomerService customerService;

	public AuthResource(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUpOperation(@RequestBody SignUpRequestDto request) {

		if (request.fullName() == null || request.fullName().isBlank()) {
			return error(HttpStatus.BAD_REQUEST, "Full name is required");
		}
		if (request.email() == null || !request.email().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
			return error(HttpStatus.BAD_REQUEST, "Valid email is required");
		}
		if (request.password() == null || request.password().length() < 6) {
			return error(HttpStatus.BAD_REQUEST, "Password must be at least 6 characters");
		}

		CustomerResponseDto customer = customerService.signUp(request);

		if (customer == null) {
			return error(HttpStatus.CONFLICT, "An account with this email already exists");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(customer);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signInOperation(@RequestBody SignInRequestDto request) {

		if (request.email() == null || request.email().isBlank()
				|| request.password() == null || request.password().isBlank()) {
			return error(HttpStatus.BAD_REQUEST, "Email and password are required");
		}

		CustomerResponseDto customer = customerService.signIn(request);

		if (customer == null) {
			return error(HttpStatus.UNAUTHORIZED, "Invalid email or password");
		}
		return ResponseEntity.ok(customer);
	}

	private ResponseEntity<Map<String, String>> error(HttpStatus status, String message) {
		return ResponseEntity.status(status).body(Map.of("message", message));
	}
}