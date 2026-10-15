package com.codegnan.app.ecommercebackend.catalogue.dto;

import java.time.LocalDateTime;

public record CustomerResponseDto(
		Long id,
		String fullName,
		String email,
		LocalDateTime createdAt) {
}