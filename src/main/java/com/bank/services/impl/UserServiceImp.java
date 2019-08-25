package com.bank.services.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.common.PasswordEncoder;
import com.bank.entities.CustomerDetails;
import com.bank.entities.CustomerEbankingVault;
import com.bank.repository.CustomerDetailsRepository;
import com.bank.repository.CustomerEBankingVaultRepository;
import com.bank.services.AccountService;
import com.bank.services.UserService;

@Service
@Transactional
public class UserServiceImp implements UserService {

	@Autowired
	private CustomerDetailsRepository customerDetailsRepo;

	@Autowired
	private CustomerEBankingVaultRepository customerEBankingVaultRepo;

	@Autowired
	private AccountService accountService;

	@Override
	public CustomerDetails registerUser(CustomerDetails customerDetails) throws NoSuchAlgorithmException {

		CustomerEbankingVault vault = new CustomerEbankingVault();

		vault.setUsername(customerDetails.getCutomerEbankingVaults().getUsername());
		vault.setEmail(customerDetails.getCutomerEbankingVaults().getEmail());
		vault.setPassword(PasswordEncoder.encryptPassword(customerDetails.getCutomerEbankingVaults().getPassword()));
		vault.setCustomerDetails(customerDetails);
		customerDetails.setCutomerEbankingVaults(vault);

		CustomerDetails details = customerDetailsRepo.save(customerDetails);
		accountService.addAccount(customerDetails);

		return details;
	}

	@Override
	public boolean findByEmail(String email) {
		Optional<CustomerEbankingVault> vault = customerEBankingVaultRepo.findByEmail(email);

		return vault.isPresent();
	}

}
