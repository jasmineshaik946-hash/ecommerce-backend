package com.codegnan.app.ecommercebackend.catalogue.service;

import com.codegnan.app.ecommercebackend.catalogue.dto.CustomerResponseDto;
import com.codegnan.app.ecommercebackend.catalogue.dto.SignInRequestDto;
import com.codegnan.app.ecommercebackend.catalogue.dto.SignUpRequestDto;

public interface CustomerService {
	CustomerResponseDto signUp(SignUpRequestDto request);

	CustomerResponseDto signIn(SignInRequestDto request);
}