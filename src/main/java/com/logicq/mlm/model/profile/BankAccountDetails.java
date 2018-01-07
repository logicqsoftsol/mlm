package com.logicq.mlm.model.profile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "BANK_ACCOUNT_DETAILS")
public class BankAccountDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 105806702118619060L;
	
	@Column(name = "BANK_NAME")
	private String bankName;
	
	@Id
	@Column(name = "ACCOUNT_NUMBER")
	private String accountNumber;
	
	@Column(name = "IFSC_CODE")
	private String ifsccode;
	
	@Column(name = "PANCARD_NO")
	private String pancardno;
	
	@Column(name = "ACCOUNT_HOLDER_NAME")
	private String accountHolderName;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "PROFILE_ID")
	private UserProfile userprofile;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfsccode() {
		return ifsccode;
	}

	public void setIfsccode(String ifsccode) {
		this.ifsccode = ifsccode;
	}

	public String getPancardno() {
		return pancardno;
	}

	public void setPancardno(String pancardno) {
		this.pancardno = pancardno;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public UserProfile getUserprofile() {
		return userprofile;
	}

	public void setUserprofile(UserProfile userprofile) {
		this.userprofile = userprofile;
	}

	@Override
	public String toString() {
		return "BankAccountDetails [bankName=" + bankName + ", accountNumber=" + accountNumber + ", ifsccode="
				+ ifsccode + ", pancardno=" + pancardno + ", accountHolderName=" + accountHolderName + ", userprofile="
				+ userprofile + "]";
	}
}
