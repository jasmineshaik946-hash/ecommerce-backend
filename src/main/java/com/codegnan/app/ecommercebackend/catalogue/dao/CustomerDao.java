package com.codegnan.app.ecommercebackend.catalogue.dao;

import com.codegnan.app.ecommercebackend.catalogue.entity.Customer;

public interface CustomerDao {
	Customer save(Customer customer);

	Customer findByEmail(String email);

	boolean existsByEmail(String email);
}