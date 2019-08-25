package com.bank.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ACCOUNT_DETAILS")
@NamedQuery(name = "AccountDetails.findAll", query = "SELECT a FROM AccountDetails a")
public class AccountDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ACC_DET_ID")
	private Integer accountDetailsId;

	@NotEmpty
	@Column(name = "BRANCH_CODE")
	private String branch;

	@NotEmpty
	@Column(name = "ACCOUNT_BASIC")
	private String accountBasic;

	@NotEmpty
	@Column(name = "ACCOUNT_SUFFIX")
	private String suffix;

	@NotEmpty
	@Column(name = "FULL_ACCOUNT_NUMBER")
	private String fullAccountNumber;

	@Column(name = "AMOUNT")
	@PositiveOrZero
	private BigDecimal amount;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "CUST_DET_ID", nullable = false)
	private CustomerDetails customerDetails;

	@OneToMany(mappedBy = "accountDetails", cascade = CascadeType.ALL)
	private List<AccountTransactionBacklog> accountTransactionBacklog;

	public AccountDetails() {
	}

	public Integer getAccountDetailsId() {
		return accountDetailsId;
	}

	public void setAccountDetailsId(Integer accountDetailsId) {
		this.accountDetailsId = accountDetailsId;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAccountBasic() {
		return accountBasic;
	}

	public void setAccountBasic(String accountBasic) {
		this.accountBasic = accountBasic;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFullAccountNumber() {
		return fullAccountNumber;
	}

	public void setFullAccountNumber(String fullAccountNumber) {
		this.fullAccountNumber = fullAccountNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public CustomerDetails getCustomerDetails() {
		return this.customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public List<AccountTransactionBacklog> getAccountTransactionBacklog() {
		return this.accountTransactionBacklog;
	}

	public void setAccountTransactionBacklog(List<AccountTransactionBacklog> accountTransactionBacklog) {
		this.accountTransactionBacklog = accountTransactionBacklog;
	}

}