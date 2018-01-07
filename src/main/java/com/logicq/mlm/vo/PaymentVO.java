package com.logicq.mlm.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentVO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4557035972891349598L;
	private String username;
	private String payewalletnumber;
	private BigDecimal amount;
	private String description;
	private String paymentmode;
	private String refrencenumber;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPaymentmode() {
		return paymentmode;
	}
	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}
	public String getRefrencenumber() {
		return refrencenumber;
	}
	public void setRefrencenumber(String refrencenumber) {
		this.refrencenumber = refrencenumber;
	}
	
	
	public String getPayewalletnumber() {
		return payewalletnumber;
	}
	public void setPayewalletnumber(String payewalletnumber) {
		this.payewalletnumber = payewalletnumber;
	}
	
	@Override
	public String toString() {
		return "PaymentVO [username=" + username + ", payewalletnumber=" + payewalletnumber + ", amount=" + amount
				+ ", description=" + description + ", paymentmode=" + paymentmode + ", refrencenumber=" + refrencenumber
				+ "]";
	}
	
}
