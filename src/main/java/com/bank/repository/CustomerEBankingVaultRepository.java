package com.bank.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.entities.CustomerEbankingVault;

@Repository
public interface CustomerEBankingVaultRepository extends CrudRepository<CustomerEbankingVault, Integer> {

	public Optional<CustomerEbankingVault> findByEmail(String email);
}
