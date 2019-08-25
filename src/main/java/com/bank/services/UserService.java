package com.bank.services;

import java.security.NoSuchAlgorithmException;

import com.bank.entities.CustomerDetails;

public interface UserService {

	public CustomerDetails registerUser(CustomerDetails customerDetails) throws NoSuchAlgorithmException;

	public boolean findByEmail(String email);
}
