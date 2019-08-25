package com.bank.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CUSTOMER_EBANKING_VAULT")
@NamedQuery(name = "CustomerEbankingVault.findAll", query = "SELECT c FROM CustomerEbankingVault c")
public class CustomerEbankingVault implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CUST_EBANKING_VAULT_ID")
	private int customerEbankingVaultId;

	@NotEmpty
	@Column(name = "USERNAME")
	private String username;

	@NotEmpty
	@Email
	@Column(name = "EMAIL_ADDRESS")
	private String email;

	@NotEmpty
	@Column(name = "PASSWORD")
	private String password;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "CUST_DET_ID", unique = true)
	@JsonIgnore
	private CustomerDetails customerDetails;

	public CustomerEbankingVault() {
	}

	public int getCustomerEbankingVaultId() {
		return this.customerEbankingVaultId;
	}

	public void setCustomerEbankingVaultId(int customerEbankingVaultId) {
		this.customerEbankingVaultId = customerEbankingVaultId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CustomerDetails getCustomerDetails() {
		return this.customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

}