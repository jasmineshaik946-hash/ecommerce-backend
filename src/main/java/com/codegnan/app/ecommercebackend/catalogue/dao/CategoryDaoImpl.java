package com.codegnan.app.ecommercebackend.catalogue.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.codegnan.app.ecommercebackend.catalogue.dto.CategoryResponseDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<CategoryResponseDto> findAllActive() {
		String jpql = """
				SELECT new com.codegnan.app.ecommercebackend.catalogue.dto.CategoryResponseDto(
					c.id, c.parent.id, c.name, c.slug, c.description
				)
				FROM Category c
				WHERE c.active = true
				ORDER BY c.name
				""";

		return entityManager.createQuery(jpql, CategoryResponseDto.class)
				.getResultList();
	}
}


