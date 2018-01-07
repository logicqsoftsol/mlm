package com.logicq.mlm.model.admin;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FEE_SETUP")
public class FeeSetup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3848036213908884881L;

	@Id
	@Column(name = "FEE_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long feeid;

	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "APPLY_TO")
	private String applyTo;

	
	
	public Long getFeeid() {
		return feeid;
	}



	public void setFeeid(Long feeid) {
		this.feeid = feeid;
	}



	public BigDecimal getAmount() {
		return amount;
	}



	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}



	public String getApplyTo() {
		return applyTo;
	}



	public void setApplyTo(String applyTo) {
		this.applyTo = applyTo;
	}



	@Override
	public String toString() {
		return "FeeSetup [feeid=" + feeid + ", amount=" + amount + ", applyTo=" + applyTo + "]";
	}

	
	
}
