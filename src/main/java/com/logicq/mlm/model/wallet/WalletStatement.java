package com.logicq.mlm.model.wallet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logicq.mlm.model.profile.WalletDetails;

@Entity
@Table(name = "WALLET_BALANCE")
public class WalletStatement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2360770268796522431L;

	@Id
	@Column(name = "WALLET_ID")
	private String walletid;

	@Column(name = "PAYOUT")
	private BigDecimal payout;

	@Column(name = "CURRENT_BALANCE")
	private BigDecimal currentbalance;

	@Column(name = "MAX_ENCASHABLE")
	private BigDecimal maxencashable;
	
	@Column(name = "ENCASHED_AMOUNT")
	private BigDecimal encashedAmount;
	

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATE")
	private Date walletlastupdate;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "WALLET_ID")
	private WalletDetails wallet;

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

	public Date getWalletlastupdate() {
		return walletlastupdate;
	}

	public void setWalletlastupdate(Date walletlastupdate) {
		this.walletlastupdate = walletlastupdate;
	}

	public WalletDetails getWallet() {
		return wallet;
	}

	public void setWallet(WalletDetails wallet) {
		this.wallet = wallet;
	}

	public BigDecimal getEncashedAmount() {
		return encashedAmount;
	}

	public void setEncashedAmount(BigDecimal encashedAmount) {
		this.encashedAmount = encashedAmount;
	}

	@Override
	public String toString() {
		return "WalletStatement [walletid=" + walletid + ", payout=" + payout + ", currentbalance=" + currentbalance
				+ ", maxencashable=" + maxencashable + ", encashedAmount=" + encashedAmount + ", walletlastupdate="
				+ walletlastupdate + ", wallet=" + wallet + "]";
	}



}
