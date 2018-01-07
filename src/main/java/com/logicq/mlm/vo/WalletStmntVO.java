package com.logicq.mlm.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WalletStmntVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5702998683083894245L;
	private String walletid;
	private BigDecimal payout;
	private BigDecimal currentbalance;
	private BigDecimal maxencashable;
	private BigDecimal encashedAmount;
	private Date walletlastupdate;
	
	public String getWalletid() {
		return walletid;
	}
	public void setWalletid(String walletid) {
		this.walletid = walletid;
	}
	public BigDecimal getPayout() {
		return payout;
	}
	public void setPayout(BigDecimal payout) {
		this.payout = payout;
	}
	public BigDecimal getCurrentbalance() {
		return currentbalance;
	}
	public void setCurrentbalance(BigDecimal currentbalance) {
		this.currentbalance = currentbalance;
	}
	public BigDecimal getMaxencashable() {
		return maxencashable;
	}
	public void setMaxencashable(BigDecimal maxencashable) {
		this.maxencashable = maxencashable;
	}
	public BigDecimal getEncashedAmount() {
		return encashedAmount;
	}
	public void setEncashedAmount(BigDecimal encashedAmount) {
		this.encashedAmount = encashedAmount;
	}
	
	
	public Date getWalletlastupdate() {
		return walletlastupdate;
	}
	public void setWalletlastupdate(Date walletlastupdate) {
		this.walletlastupdate = walletlastupdate;
	}
	
	@Override
	public String toString() {
		return "WalletStmntVO [walletid=" + walletid + ", payout=" + payout + ", currentbalance=" + currentbalance
				+ ", maxencashable=" + maxencashable + ", encashedAmount=" + encashedAmount + ", walletlastupdate="
				+ walletlastupdate + "]";
	}


}
