package com.bank.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.entities.AccountDetails;

@Repository
public interface AccountDetailsRepository extends CrudRepository<AccountDetails, Integer> {

	public AccountDetails findTopByOrderByAccountDetailsIdDesc();
}
