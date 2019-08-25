package com.bank.services.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.common.ResourceNotFoundException;
import com.bank.common.TransactionUtils;
import com.bank.entities.AccountDetails;
import com.bank.entities.AccountTransactionBacklog;
import com.bank.entities.CustomerDetails;
import com.bank.repository.AccountDetailsRepository;
import com.bank.repository.AccountTransactionBacklogRepository;
import com.bank.services.AccountService;

@Service
@Transactional
public class AccountServiceImp implements AccountService {

	@Autowired
	private AccountDetailsRepository accountDetailsRepo;

	@Autowired
	private AccountTransactionBacklogRepository accountTransactionBacklogRepository;

	@Override
	public void addAccount(CustomerDetails customerDetails) {
		AccountDetails account = new AccountDetails();

		account.setCustomerDetails(customerDetails);
		account.setAccountBasic(String.valueOf((int) ((Math.random() * 90000000) + 10000000)));
		account.setBranch(String.valueOf((int) ((Math.random() * 900) + 100)));
		account.setSuffix(String.valueOf((int) ((Math.random() * 900) + 100)));
		account.setFullAccountNumber(account.getBranch() + account.getAccountBasic() + account.getSuffix());
		account.setAmount(BigDecimal.ZERO);
		accountDetailsRepo.save(account);
	}

	@Override
	public AccountDetails debitAmount(AccountDetails accountDetails, Integer accountId)
			throws ResourceNotFoundException {
		Optional<AccountDetails> existingAccount = accountDetailsRepo.findById(accountId);

		if (!existingAccount.isPresent())
			throw new ResourceNotFoundException("Record not found");
		else {

			if (existingAccount.get().getAmount().compareTo(accountDetails.getAmount()) < 0) {
				throw new ResourceNotFoundException("Invalid Amount");
			}
			BigDecimal transactionAmount = accountDetails.getAmount();

			accountDetails.setAccountDetailsId(existingAccount.get().getAccountDetailsId());
			accountDetails.setCustomerDetails(existingAccount.get().getCustomerDetails());
			accountDetails.setAccountBasic(existingAccount.get().getAccountBasic());
			accountDetails.setBranch(existingAccount.get().getBranch());
			accountDetails.setSuffix(existingAccount.get().getSuffix());
			accountDetails.setFullAccountNumber(existingAccount.get().getFullAccountNumber());
			accountDetails.setAmount(existingAccount.get().getAmount().subtract(accountDetails.getAmount()));

			List<AccountTransactionBacklog> baclogList = new ArrayList<AccountTransactionBacklog>();
			AccountTransactionBacklog backlog = new AccountTransactionBacklog();

			backlog.setAccountDetails(accountDetails);
			backlog.setTreansactionDate(new Timestamp(new Date().getTime()));
			backlog.setTransactionAmount(transactionAmount);
			backlog.setTransactionMode(TransactionUtils.TransactionMode.DEBIT.toString());
			backlog.setTransactionType(TransactionUtils.TransactionType.ATM.toString());
			backlog.setBalanceAfterTransaction(accountDetails.getAmount());

			baclogList.add(backlog);
			accountDetails.setAccountTransactionBacklog(baclogList);

			return accountDetailsRepo.save(accountDetails);
		}
	}

	@Override
	public AccountDetails creditAmount(AccountDetails accountDetails, Integer accountId)
			throws ResourceNotFoundException {
		Optional<AccountDetails> existingAccount = accountDetailsRepo.findById(accountId);

		if (!existingAccount.isPresent())
			throw new ResourceNotFoundException("Record not found");
		else {
			BigDecimal transactionAmount = accountDetails.getAmount();

			accountDetails.setAccountDetailsId(existingAccount.get().getAccountDetailsId());
			accountDetails.setCustomerDetails(existingAccount.get().getCustomerDetails());
			accountDetails.setAccountBasic(existingAccount.get().getAccountBasic());
			accountDetails.setBranch(existingAccount.get().getBranch());
			accountDetails.setSuffix(existingAccount.get().getSuffix());
			accountDetails.setFullAccountNumber(existingAccount.get().getFullAccountNumber());
			accountDetails.setAmount(existingAccount.get().getAmount().add(accountDetails.getAmount()));

			List<AccountTransactionBacklog> baclogList = new ArrayList<AccountTransactionBacklog>();
			AccountTransactionBacklog backlog = new AccountTransactionBacklog();

			backlog.setAccountDetails(accountDetails);
			backlog.setTreansactionDate(new Timestamp(new Date().getTime()));
			backlog.setTransactionAmount(transactionAmount);
			backlog.setTransactionMode(TransactionUtils.TransactionMode.CREDIT.toString());
			backlog.setTransactionType(TransactionUtils.TransactionType.CHECK.toString());
			backlog.setBalanceAfterTransaction(accountDetails.getAmount());

			baclogList.add(backlog);
			accountDetails.setAccountTransactionBacklog(baclogList);

			return accountDetailsRepo.save(accountDetails);
		}
	}

	@Override
	public List<AccountTransactionBacklog> findByAccountDetailsId(Integer accountId) {
		AccountDetails account = new AccountDetails();
		account.setAccountDetailsId(accountId);
		return accountTransactionBacklogRepository.findByAccountDetails(account);
	}

	public AccountDetails findTopByOrderByIdDesc() {
		return accountDetailsRepo.findTopByOrderByAccountDetailsIdDesc();
	}

}
