package com.codegnan.app.ecommercebackend.catalogue.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.codegnan.app.ecommercebackend.catalogue.dto.ProductRequestDto;
import com.codegnan.app.ecommercebackend.catalogue.dto.ProductResponseDto;
import com.codegnan.app.ecommercebackend.catalogue.entity.Product;
import com.codegnan.app.ecommercebackend.catalogue.entity.ProductStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class ProductDaoImpl implements ProductDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Long save(ProductRequestDto productRequestDto) {
		Product product = new Product();
		product.setName(productRequestDto.name());
		product.setBrand(productRequestDto.brand());
		product.setDescription(productRequestDto.description());
		product.setPrice(productRequestDto.price());
		product.setImageUrl(productRequestDto.imageUrl());
		if (productRequestDto.status() != null) {
			product.setStatus(ProductStatus.from(productRequestDto.status()));
		}

		entityManager.persist(product);
		return product.getId();
	}

	@Override
	public ProductResponseDto findById(long productId) {
		Product product = entityManager.find(Product.class, productId);
		if (product == null) {
			return null;
		}
		return mapToDto(product);
	}

	@Override
	public List<ProductResponseDto> findAll() {
		String jpql = "SELECT p FROM Product p";
		TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
		List<Product> products = query.getResultList();

		return products.stream().map(this::mapToDto).toList();
	}

	@Override
	public List<ProductResponseDto> findActiveByCategoryId(long categoryId) {
		String jpql = """
				SELECT DISTINCT p FROM Product p 
				JOIN p.categories c 
				WHERE p.status = com.codegnan.app.ecommercebackend.catalogue.entity.ProductStatus.ACTIVE 
				AND c.id = :categoryId
				""";
		TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
		query.setParameter("categoryId", categoryId);
		List<Product> products = query.getResultList();

		return products.stream().map(this::mapToDto).toList();
	}

	@Override
	@Transactional
	public boolean updateName(long productId, String updatedName) {
		Product product = entityManager.find(Product.class, productId);
		if (product != null) {
			product.setName(updatedName);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean delete(long productId) {
		Product product = entityManager.find(Product.class, productId);
		if (product != null) {
			entityManager.remove(product);
			return true;
		}
		return false;
	}

	private ProductResponseDto mapToDto(Product product) {
		return new ProductResponseDto(
				product.getId(),
				product.getName(),
				product.getBrand(),
				product.getDescription(),
				product.getPrice(),
				product.getImageUrl(),
				product.getStatus() != null ? product.getStatus().name() : "ACTIVE",
				product.getCreatedAt(),
				product.getUpdatedAt()
		);
	}
}