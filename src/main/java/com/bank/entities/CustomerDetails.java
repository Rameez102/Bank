package com.bank.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CUSTOMER_DETAILS")
@NamedQuery(name = "CustomerDetails.findAll", query = "SELECT c FROM CustomerDetails c")
public class CustomerDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CUST_DET_ID")
	private Integer customerDetailId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "NATIONALITY")
	private String nationality;

	@Column(name = "PASSPORT_NUMBER")
	private String passportNumber;

	@Column(name = "CONTACT_NUMBER")
	private String contactNumber;

	@Column(name = "ADDRESS")
	private String address;

	@OneToOne(mappedBy = "customerDetails", cascade = CascadeType.ALL)
	private CustomerEbankingVault cutomerEbankingVaults;

	@JsonIgnore
	@OneToMany(mappedBy = "customerDetails")
	private List<AccountDetails> accountDetails;

	public CustomerDetails() {
	}

	public Integer getCustomerDetailId() {
		return customerDetailId;
	}

	public void setCustomerDetailId(Integer customerDetailId) {
		this.customerDetailId = customerDetailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public CustomerEbankingVault getCutomerEbankingVaults() {
		return this.cutomerEbankingVaults;
	}

	public void setCutomerEbankingVaults(CustomerEbankingVault cutomerEbankingVaults) {
		this.cutomerEbankingVaults = cutomerEbankingVaults;
	}

	public List<AccountDetails> getAccountDetails() {
		return this.accountDetails;
	}

	public void setAccountDetails(List<AccountDetails> accountDetails) {
		this.accountDetails = accountDetails;
	}

}