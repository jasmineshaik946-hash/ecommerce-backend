package com.codegnan.app.ecommercebackend.catalogue.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.codegnan.app.ecommercebackend.catalogue.entity.Customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Customer save(Customer customer) {
		entityManager.persist(customer);
		return customer;
	}

	@Override
	public Customer findByEmail(String email) {
		List<Customer> result = entityManager
				.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
				.setParameter("email", email)
				.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public boolean existsByEmail(String email) {
		Long count = entityManager
				.createQuery("SELECT COUNT(c) FROM Customer c WHERE c.email = :email", Long.class)
				.setParameter("email", email)
				.getSingleResult();
		return count > 0;
	}
}