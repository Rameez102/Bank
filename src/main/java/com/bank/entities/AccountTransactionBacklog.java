package com.bank.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ACCOUNT_TRANSACTION_BACKLOG")
@NamedQuery(name = "AccountTransactionBacklog.findAll", query = "SELECT a FROM AccountTransactionBacklog a")
public class AccountTransactionBacklog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ACC_TRANS_LOG_ID")
	private int accountTransactionBacklogId;

	@NotEmpty
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;

	@NotEmpty
	@Column(name = "TRANSACTION_MODE")
	private String transactionMode;

	@Column(name = "TRANSACTION_AMOUNT")
	@PositiveOrZero
	private BigDecimal transactionAmount;

	@Column(name = "BALANCE_AFTER_TRANSACTION")
	@PositiveOrZero
	private BigDecimal balanceAfterTransaction;

	@Column(name = "TRANSACTION_DATE", nullable = true, updatable = true)
	@Version
	private Timestamp treansactionDate;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "ACC_DET_ID", nullable = false)
	private AccountDetails accountDetails;

	public AccountTransactionBacklog() {
	}

	public int getAccountTransactionBacklogId() {
		return this.accountTransactionBacklogId;
	}

	public void setAccountTransactionBacklogId(int accountTransactionBacklogId) {
		this.accountTransactionBacklogId = accountTransactionBacklogId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(String transactionMode) {
		this.transactionMode = transactionMode;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public BigDecimal getBalanceAfterTransaction() {
		return balanceAfterTransaction;
	}

	public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
		this.balanceAfterTransaction = balanceAfterTransaction;
	}

	public Timestamp getTreansactionDate() {
		return treansactionDate;
	}

	public void setTreansactionDate(Timestamp treansactionDate) {
		this.treansactionDate = treansactionDate;
	}

	public AccountDetails getAccountDetails() {
		return this.accountDetails;
	}

	public void setAccountDetails(AccountDetails accountDetails) {
		this.accountDetails = accountDetails;
	}

}