package com.bank.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.common.ResourceNotFoundException;
import com.bank.entities.AccountDetails;
import com.bank.entities.AccountTransactionBacklog;
import com.bank.entities.CustomerDetails;
import com.bank.services.AccountService;
import com.bank.services.UserService;

@RequestMapping("/bank")
@RestController
public class BankController {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@PostMapping(value = "/registerUser")
	public ResponseEntity<Object> registerUser(@Valid @RequestBody CustomerDetails customerDetails)
			throws NoSuchAlgorithmException {

		if (userService.findByEmail(customerDetails.getCutomerEbankingVaults().getEmail())) {
			return new ResponseEntity<Object>("User is already registered with this email", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(userService.registerUser(customerDetails), HttpStatus.CREATED);
	}

	@PutMapping("/debit/{accountId}")
	public ResponseEntity<Object> debit(@RequestBody AccountDetails accountDetails, @PathVariable Integer accountId)
			throws ResourceNotFoundException {

		return new ResponseEntity<Object>(accountService.debitAmount(accountDetails, accountId), HttpStatus.OK);
	}

	@PutMapping("/credit/{accountId}")
	public ResponseEntity<Object> credit(@RequestBody AccountDetails accountDetails, @PathVariable Integer accountId)
			throws ResourceNotFoundException {

		return new ResponseEntity<Object>(accountService.creditAmount(accountDetails, accountId), HttpStatus.OK);
	}

	@GetMapping("/getAccountStatement/{accountId}")
	public ResponseEntity<List<AccountTransactionBacklog>> getAccountStatement(@PathVariable Integer accountId)
			throws ResourceNotFoundException {

		return new ResponseEntity<List<AccountTransactionBacklog>>(accountService.findByAccountDetailsId(accountId),
				HttpStatus.OK);
	}

}