package com.codegnan.app.ecommercebackend.catalogue.dto;

public record CategoryResponseDto(
		Long id, 
		Long parentId, 
		String name, 
		String slug, 
		String description) {
}