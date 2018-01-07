package com.logicq.mlm.vo;

import java.math.BigDecimal;
import java.util.Date;

public class EncashVO extends StatusVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2275774364728648409L;
	private String walletnumber;
	private BigDecimal encashamount;
	private String walletid;
	private Date requestdate;
	private String username;
	private String encashtype;
	private String refrencenumber;
	private String description;
	private String paymentmode;

	
	public BigDecimal getEncashamount() {
		return encashamount;
	}
	public void setEncashamount(BigDecimal encashamount) {
		this.encashamount = encashamount;
	}
	public String getWalletid() {
		return walletid;
	}
	public void setWalletid(String walletid) {
		this.walletid = walletid;
	}
	public Date getRequestdate() {
		return requestdate;
	}
	public void setRequestdate(Date requestdate) {
		this.requestdate = requestdate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEncashtype() {
		return encashtype;
	}
	public void setEncashtype(String encashtype) {
		this.encashtype = encashtype;
	}
	public String getRefrencenumber() {
		return refrencenumber;
	}
	public void setRefrencenumber(String refrencenumber) {
		this.refrencenumber = refrencenumber;
	}
	public String getWalletnumber() {
		return walletnumber;
	}
	public void setWalletnumber(String walletnumber) {
		this.walletnumber = walletnumber;
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
	@Override
	public String toString() {
		return "EncashVO [walletnumber=" + walletnumber + ", encashamount=" + encashamount + ", walletid=" + walletid
				+ ", requestdate=" + requestdate + ", username=" + username + ", encashtype=" + encashtype
				+ ", refrencenumber=" + refrencenumber + ", description=" + description + ", paymentmode=" + paymentmode
				+ "]";
	}
}
