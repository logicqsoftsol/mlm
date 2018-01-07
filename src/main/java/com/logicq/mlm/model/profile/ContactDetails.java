package com.logicq.mlm.model.profile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CONTACT_DETAILS")
public class ContactDetails implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8173471188983797946L;
	
	@Id
	@Column(name = "CONTACT_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contactid;

	@Column(name = "ADDRESS_TEXT")
	private String addressText;
	
	@Column(name = "LAND_MARK")
	private String landMark;
	
	@Column(name = "PINCODE")
	private String pincode;
	
	@Column(name = "DISTRICT")
	private String district;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobilenumber;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ALTER_MOB")
	private String alternetmobilenumber;
	
	@Column(name = "IS_VALID_ADDRESS")
	private Boolean isaddressvalid;
	
	@Column(name = "IS_VALID_EMAIL")
	private Boolean isvalidemail;
	
	@Column(name = "IS_VALID_MOB_NUMBER")
	private Boolean isvalidmobilenumber;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "PROFILE_ID")
	private UserProfile userprofile;



	public String getAddressText() {
		return addressText;
	}

	public void setAddressText(String addressText) {
		this.addressText = addressText;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlternetmobilenumber() {
		return alternetmobilenumber;
	}

	public void setAlternetmobilenumber(String alternetmobilenumber) {
		this.alternetmobilenumber = alternetmobilenumber;
	}

	public Boolean getIsaddressvalid() {
		return isaddressvalid;
	}

	public void setIsaddressvalid(Boolean isaddressvalid) {
		this.isaddressvalid = isaddressvalid;
	}

	public UserProfile getUserprofile() {
		return userprofile;
	}

	public void setUserprofile(UserProfile userprofile) {
		this.userprofile = userprofile;
	}

	public Long getContactid() {
		return contactid;
	}

	public void setContactid(Long contactid) {
		this.contactid = contactid;
	}

	public Boolean getIsvalidemail() {
		return isvalidemail;
	}

	public void setIsvalidemail(Boolean isvalidemail) {
		this.isvalidemail = isvalidemail;
	}

	public Boolean getIsvalidmobilenumber() {
		return isvalidmobilenumber;
	}

	public void setIsvalidmobilenumber(Boolean isvalidmobilenumber) {
		this.isvalidmobilenumber = isvalidmobilenumber;
	}

	@Override
	public String toString() {
		return "ContactDetails [contactid=" + contactid + ", addressText=" + addressText + ", landMark=" + landMark
				+ ", pincode=" + pincode + ", district=" + district + ", state=" + state + ", country=" + country
				+ ", mobilenumber=" + mobilenumber + ", email=" + email + ", alternetmobilenumber="
				+ alternetmobilenumber + ", isaddressvalid=" + isaddressvalid + ", isvalidemail=" + isvalidemail
				+ ", isvalidmobilenumber=" + isvalidmobilenumber +"]";
	}

	
}
