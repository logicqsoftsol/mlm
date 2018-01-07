package com.logicq.mlm.model.sms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OTP_DETAILS")
public class OTPDetails implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8319479465268667179L;

	@Id
	@Column(name = "OTP_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "MOBILE_NUMBER")
	private String mobilenumber;
	
	@Column(name = "OTP_NUMBER", nullable = false)
	private Integer otpnumber;
	
	@Column(name = "IS_VALIDATE", nullable = false)
	private boolean otpvalidate;
	
	@Column(name = "VALIDATION_DATE", nullable = true)
	private Date otpvalidationdate;
	
	
	@Column(name = "SENDER_EMAIL_ID")
	private String senderemailid;

	@Column(name = "RECIVER_EMAIL_ID")
	private String reciveremailid;
	
	
	

	public String getMobilenumber() {
		return mobilenumber;
	}


	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}


	public Integer getOtpnumber() {
		return otpnumber;
	}


	public void setOtpnumber(Integer otpnumber) {
		this.otpnumber = otpnumber;
	}


	public boolean isOtpvalidate() {
		return otpvalidate;
	}


	public void setOtpvalidate(boolean otpvalidate) {
		this.otpvalidate = otpvalidate;
	}


	public Date getOtpvalidationdate() {
		return otpvalidationdate;
	}


	public void setOtpvalidationdate(Date otpvalidationdate) {
		this.otpvalidationdate = otpvalidationdate;
	}


	public String getSenderemailid() {
		return senderemailid;
	}


	public void setSenderemailid(String senderemailid) {
		this.senderemailid = senderemailid;
	}


	public String getReciveremailid() {
		return reciveremailid;
	}


	public void setReciveremailid(String reciveremailid) {
		this.reciveremailid = reciveremailid;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "OTPDetails [id=" + id + ", mobilenumber=" + mobilenumber + ", otpnumber=" + otpnumber + ", otpvalidate="
				+ otpvalidate + ", otpvalidationdate=" + otpvalidationdate + ", senderemailid=" + senderemailid
				+ ", reciveremailid=" + reciveremailid + "]";
	}




}
