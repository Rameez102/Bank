package com.bank.assignment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.common.PasswordEncoder;
import com.bank.common.TransactionUtils;
import com.bank.entities.AccountDetails;
import com.bank.entities.AccountTransactionBacklog;
import com.bank.entities.CustomerDetails;
import com.bank.entities.CustomerEbankingVault;
import com.bank.services.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Autowired
	private AccountService accountService;

	@Test
	public void registerUser() throws Exception {

		CustomerDetails customerDetails = new CustomerDetails();
		CustomerEbankingVault vault = new CustomerEbankingVault();

		customerDetails.setFirstName("Test case1");
		customerDetails.setLastName("Test case122");
		customerDetails.setNationality("Pak1");
		customerDetails.setContactNumber("9999");
		customerDetails.setPassportNumber("AS22");
		customerDetails.setAddress("dada");

		vault.setUsername("test case usr");
		vault.setEmail("testcase@abc.com");
		vault.setPassword(PasswordEncoder.encryptPassword("pass"));
		vault.setCustomerDetails(customerDetails);
		customerDetails.setCutomerEbankingVaults(vault);

		HttpEntity<CustomerDetails> entity = new HttpEntity<CustomerDetails>(customerDetails, headers);

		ResponseEntity<String> response = restTemplate.exchange(getURL("/bank/registerUser"), HttpMethod.POST, entity,
				String.class);
		// verify
		int status = response.getStatusCodeValue();
		assertEquals(201, status);

	}

	@Test
	public void creditAmount() throws Exception {

		AccountDetails existingAccount = accountService.findTopByOrderByIdDesc();
		existingAccount.setAmount(BigDecimal.valueOf(50));

		List<AccountTransactionBacklog> baclogList = new ArrayList<AccountTransactionBacklog>();
		AccountTransactionBacklog backlog = new AccountTransactionBacklog();

		backlog.setAccountDetails(existingAccount);
		backlog.setTreansactionDate(new Timestamp(new Date().getTime()));
		backlog.setTransactionAmount(BigDecimal.valueOf(50));
		backlog.setTransactionMode(TransactionUtils.TransactionMode.CREDIT.toString());
		backlog.setTransactionType(TransactionUtils.TransactionType.CHECK.toString());
		backlog.setBalanceAfterTransaction(existingAccount.getAmount());

		baclogList.add(backlog);
		existingAccount.setAccountTransactionBacklog(baclogList);

		HttpEntity<AccountDetails> entity = new HttpEntity<AccountDetails>(existingAccount, headers);

		ResponseEntity<String> response = restTemplate.exchange(getURL("/bank/credit/{accountId}"), HttpMethod.PUT,
				entity, String.class, existingAccount.getAccountDetailsId());
		// verify
		int status = response.getStatusCodeValue();
		assertEquals(200, status);

	}

	@Test
	public void debitAmount() throws Exception {

		AccountDetails existingAccount = accountService.findTopByOrderByIdDesc();
		existingAccount.setAmount(BigDecimal.valueOf(50));

		List<AccountTransactionBacklog> baclogList = new ArrayList<AccountTransactionBacklog>();
		AccountTransactionBacklog backlog = new AccountTransactionBacklog();

		backlog.setAccountDetails(existingAccount);
		backlog.setTreansactionDate(new Timestamp(new Date().getTime()));
		backlog.setTransactionAmount(BigDecimal.valueOf(50));
		backlog.setTransactionMode(TransactionUtils.TransactionMode.CREDIT.toString());
		backlog.setTransactionType(TransactionUtils.TransactionType.CHECK.toString());
		backlog.setBalanceAfterTransaction(existingAccount.getAmount());

		baclogList.add(backlog);
		existingAccount.setAccountTransactionBacklog(baclogList);

		HttpEntity<AccountDetails> entity = new HttpEntity<AccountDetails>(existingAccount, headers);

		ResponseEntity<String> response = restTemplate.exchange(getURL("/bank/debit/{accountId}"), HttpMethod.PUT,
				entity, String.class, existingAccount.getAccountDetailsId());
		// verify
		int status = response.getStatusCodeValue();
		assertEquals(200, status);

	}

	@Test
	public void getAccountStatement() throws Exception {

		AccountDetails existingAccount = accountService.findTopByOrderByIdDesc();

		ResponseEntity<List<AccountTransactionBacklog>> response = restTemplate.exchange(
				getURL("/bank/getAccountStatement/{accountId}"), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<AccountTransactionBacklog>>() {
				}, existingAccount.getAccountDetailsId());

		int status = response.getStatusCodeValue();
		List<AccountTransactionBacklog> resultItem = response.getBody();

		// verify
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		assertNotNull(resultItem);

	}

	private String getURL(String uri) {
		return "http://localhost:" + port + uri;
	}

}
