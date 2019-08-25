package com.bank.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.entities.AccountDetails;
import com.bank.entities.AccountTransactionBacklog;

@Repository
public interface AccountTransactionBacklogRepository extends CrudRepository<AccountTransactionBacklog, Integer> {

	public List<AccountTransactionBacklog> findByAccountDetails(AccountDetails accountDetails);
}
