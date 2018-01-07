package com.logicq.mlm.model.profile;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DOCUMENT_DETAILS")
public class UserDocument implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8190423721295652399L;

	@Id
	@Column(name = "DOCUEMNT_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long documentID;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name = "USER_NAME")
	private String username;
	
	@Column(name = "PROFILE_ID")
	private Long profileID;
	
	@Column(name = "PATH")
	private String documentPath;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "UPLOAD_DATE", nullable = true)
	private Date uploadDate;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "UPLOAD_FOR")
	private String uploadFor;
	
	
	@Column(name = "SERVICE_TYPE")
	private String serviceType;

	public Long getDocumentID() {
		return documentID;
	}

	public void setDocumentID(Long documentID) {
		this.documentID = documentID;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUploadFor() {
		return uploadFor;
	}

	public void setUploadFor(String uploadFor) {
		this.uploadFor = uploadFor;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getProfileID() {
		return profileID;
	}

	public void setProfileID(Long profileID) {
		this.profileID = profileID;
	}

	@Override
	public String toString() {
		return "UserDocument [documentID=" + documentID + ", mobileNumber=" + mobileNumber + ", username=" + username
				+ ", profileID=" + profileID + ", documentPath=" + documentPath + ", uploadDate=" + uploadDate
				+ ", name=" + name + ", uploadFor=" + uploadFor + ", serviceType=" + serviceType + "]";
	}

	
}
