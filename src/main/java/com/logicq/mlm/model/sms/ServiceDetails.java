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
@Table(name = "SERVICE_DETAILS")
public class ServiceDetails implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8460558880785591129L;

	@Id
	@Column(name = "SERVICE_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@Column(name = "SERVICE_NAME", nullable = true)
	private String servicename;
	
	@Column(name = "SERVICE_DATE", nullable = true)
	private Date servicedate;
	
	@Column(name = "SERVICE_TIME", nullable = true)
	private String servicetime;
	
	@Column(name = "CUSTOMER_NAME", nullable = false)
	private String customername;
	
	
	@Column(name = "CUSTMOER_MOB", nullable = false)
	private String customermob;
	
	
	@Column(name = "CUSTOMER_ADDRESS", nullable = false)
	private String customeraddress;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public Date getServicedate() {
		return servicedate;
	}

	public void setServicedate(Date servicedate) {
		this.servicedate = servicedate;
	}

	public String getServicetime() {
		return servicetime;
	}

	public void setServicetime(String servicetime) {
		this.servicetime = servicetime;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getCustomermob() {
		return customermob;
	}

	public void setCustomermob(String customermob) {
		this.customermob = customermob;
	}

	public String getCustomeraddress() {
		return customeraddress;
	}

	public void setCustomeraddress(String customeraddress) {
		this.customeraddress = customeraddress;
	}

	
	@Override
	public String toString() {
		return "ServiceDetails [id=" + id + ", servicename=" + servicename + ", servicedate=" + servicedate
				+ ", servicetime=" + servicetime + ", customername=" + customername + ", customermob=" + customermob
				+ ", customeraddress=" + customeraddress + "]";
	}

	
	

}
