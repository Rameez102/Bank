package com.bank.services;

import java.util.List;

import com.bank.common.ResourceNotFoundException;
import com.bank.entities.AccountDetails;
import com.bank.entities.AccountTransactionBacklog;
import com.bank.entities.CustomerDetails;

public interface AccountService {

	public void addAccount(CustomerDetails customerDetails);

	public AccountDetails debitAmount(AccountDetails accountDetails, Integer accountId)
			throws ResourceNotFoundException;

	public AccountDetails creditAmount(AccountDetails accountDetails, Integer accountId)
			throws ResourceNotFoundException;

	public List<AccountTransactionBacklog> findByAccountDetailsId(Integer accountId);

	public AccountDetails findTopByOrderByIdDesc();

}
