package com.codegnan.app.ecommercebackend.catalogue.dto;

public record SignUpRequestDto(
		String fullName,
		String email,
		String password) {
}