package com.codegnan.app.ecommercebackend.catalogue.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codegnan.app.ecommercebackend.catalogue.dao.CustomerDao;
import com.codegnan.app.ecommercebackend.catalogue.dto.CustomerResponseDto;
import com.codegnan.app.ecommercebackend.catalogue.dto.SignInRequestDto;
import com.codegnan.app.ecommercebackend.catalogue.dto.SignUpRequestDto;
import com.codegnan.app.ecommercebackend.catalogue.entity.Customer;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private final CustomerDao customerDao;

	@Autowired
	public CustomerServiceImpl(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	// returns null if the email already exists
	@Override
	public CustomerResponseDto signUp(SignUpRequestDto request) {
		String email = request.email().trim().toLowerCase(Locale.ROOT);

		if (customerDao.existsByEmail(email)) {
			return null;
		}

		Customer customer = new Customer();
		customer.setFullName(request.fullName().trim());
		customer.setEmail(email);
		customer.setPassword(request.password());
		customer.setActive(true);

		Customer saved = customerDao.save(customer);
		return new CustomerResponseDto(saved.getId(), saved.getFullName(), saved.getEmail(), saved.getCreatedAt());
	}

	// returns null if email or password is wrong
	@Override
	@Transactional(readOnly = true)
	public CustomerResponseDto signIn(SignInRequestDto request) {
		String email = request.email().trim().toLowerCase(Locale.ROOT);
		Customer customer = customerDao.findByEmail(email);

		if (customer == null || !customer.isActive() || !customer.getPassword().equals(request.password())) {
			return null;
		}

		return new CustomerResponseDto(customer.getId(), customer.getFullName(), customer.getEmail(),
				customer.getCreatedAt());
	}
}