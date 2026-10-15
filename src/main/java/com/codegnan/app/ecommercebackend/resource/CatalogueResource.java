package com.codegnan.app.ecommercebackend.resource;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codegnan.app.ecommercebackend.catalogue.dto.CategoryResponseDto;
import com.codegnan.app.ecommercebackend.catalogue.dto.ProductRequestDto;
import com.codegnan.app.ecommercebackend.catalogue.dto.ProductResponseDto;
import com.codegnan.app.ecommercebackend.catalogue.service.CategoryService;
import com.codegnan.app.ecommercebackend.catalogue.service.ProductService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/rest/api/v1/catalogues")
public class CatalogueResource {

	private final ProductService productService;
	private final CategoryService categoryService;

	public CatalogueResource(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@PostMapping
	public ResponseEntity<String> addProductOperation(@RequestBody ProductRequestDto productRequestDto) {
		productService.addProduct(productRequestDto);
		return ResponseEntity.ok("success");
	}

	@GetMapping
	public ResponseEntity<ProductResponseDto> searchProductOperation(@RequestParam(required = false, defaultValue = "0") long productId) {
		if (productId == 0) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(productService.searchProductById(productId));
	}

	@GetMapping("/viewall")
	public ResponseEntity<List<ProductResponseDto>> getAllProductsOperation() {
		return ResponseEntity.ok(productService.getAllProducts());
	}

	@GetMapping("/categories")
	public ResponseEntity<List<CategoryResponseDto>> getCategoriesOperation() {
		return ResponseEntity.ok(categoryService.getActiveCategories());
	}

	@GetMapping("/categories/{categoryId}/products")
	public ResponseEntity<List<ProductResponseDto>> getProductsByCategoryOperation(@PathVariable long categoryId) {
		return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
	}

	@PutMapping
	public ResponseEntity<String> updateProductNameOperation(@RequestParam long productId, @RequestParam String updatedName) {
		productService.renameProduct(productId, updatedName);
		return ResponseEntity.ok("success");
	}

	@DeleteMapping
	public ResponseEntity<String> removeProductOperation(@RequestParam long productId) {
		productService.removeProduct(productId);
		return ResponseEntity.ok("success");
	}
}