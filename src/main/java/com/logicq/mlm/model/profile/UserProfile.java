package com.logicq.mlm.model.profile;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.logicq.mlm.model.login.Login;

@Entity
@Table(name = "USER_PROFILE")
public class UserProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6997948080797818976L;

	@Id
	@Column(name = "PROFILE_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstname;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastname;

	@Column(name = "GENDER", nullable = true)
	private String gender;

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTH_DATE", nullable = true)
	private Date dateofbirth;

	@OneToOne(mappedBy = "userprofile", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Login logindetails;

	@OneToOne(mappedBy = "userprofile", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private NetworkInfo networkinfo;

	@OneToOne(mappedBy = "userprofile", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ContactDetails conatctDetails;

	@OneToOne(mappedBy = "userprofile", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private WalletDetails walletdetails;

	@OneToOne(mappedBy = "userprofile", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private SocialDetails socialdetails;
	
	@OneToOne(mappedBy = "userprofile", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private BankAccountDetails bankAccountDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public Login getLogindetails() {
		return logindetails;
	}

	public void setLogindetails(Login logindetails) {
		this.logindetails = logindetails;
	}

	public NetworkInfo getNetworkinfo() {
		return networkinfo;
	}

	public void setNetworkinfo(NetworkInfo networkinfo) {
		this.networkinfo = networkinfo;
	}

	public ContactDetails getConatctDetails() {
		return conatctDetails;
	}

	public void setConatctDetails(ContactDetails conatctDetails) {
		this.conatctDetails = conatctDetails;
	}

	public WalletDetails getWalletdetails() {
		return walletdetails;
	}

	public void setWalletdetails(WalletDetails walletdetails) {
		this.walletdetails = walletdetails;
	}

	public SocialDetails getSocialdetails() {
		return socialdetails;
	}

	public void setSocialdetails(SocialDetails socialdetails) {
		this.socialdetails = socialdetails;
	}

	public BankAccountDetails getBankAccountDetails() {
		return bankAccountDetails;
	}

	public void setBankAccountDetails(BankAccountDetails bankAccountDetails) {
		this.bankAccountDetails = bankAccountDetails;
	}

	
	@Override
	public String toString() {
		return "UserProfile [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", gender=" + gender
				+ ", dateofbirth=" + dateofbirth + ", logindetails=" + logindetails + ", networkinfo=" + networkinfo
				+ ", conatctDetails=" + conatctDetails + ", walletdetails=" + walletdetails + ", socialdetails="
				+ socialdetails + ", bankAccountDetails=" + bankAccountDetails + "]";
	}

}
