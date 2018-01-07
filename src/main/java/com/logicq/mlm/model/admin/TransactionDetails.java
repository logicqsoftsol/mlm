package com.logicq.mlm.model.admin;

import java.math.BigDecimal;
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
@Table(name = "TXN_DETAILS")
public class TransactionDetails {

	@Id
	@Column(name = "TXN_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long txnid;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "TXN_DATE")
	private Date txndate;

	@Column(name = "TXN_AMOUT", nullable = false)
	private BigDecimal amount;
	
	@Column(name = "MODE_OF_TXN",nullable = false)
	private String modeoftxn;
	
	@Column(name = "TXN_REFRENCE_NO",nullable = false)
	private String refrenceno;
	
	@Column(name = "TXN_FOR")
	private String txnfor;
	
	@Column(name = "WALLET_ID",nullable = false)
	private String walletid;
	
	@Column(name = "TXN_TYPE")
	private String txntype;
	
	@Column(name = "TXN_NUMBER",nullable = false)
	private String txnNumber;


	public Long getTxnid() {
		return txnid;
	}

	public void setTxnid(Long txnid) {
		this.txnid = txnid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTxndate() {
		return txndate;
	}

	public void setTxndate(Date txndate) {
		this.txndate = txndate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getModeoftxn() {
		return modeoftxn;
	}

	public void setModeoftxn(String modeoftxn) {
		this.modeoftxn = modeoftxn;
	}

	public String getRefrenceno() {
		return refrenceno;
	}

	public void setRefrenceno(String refrenceno) {
		this.refrenceno = refrenceno;
	}

	public String getTxnfor() {
		return txnfor;
	}

	public void setTxnfor(String txnfor) {
		this.txnfor = txnfor;
	}

	public String getWalletid() {
		return walletid;
	}

	public void setWalletid(String walletid) {
		this.walletid = walletid;
	}
	
	

	public String getTxntype() {
		return txntype;
	}

	public void setTxntype(String txntype) {
		this.txntype = txntype;
	}

	public String getTxnNumber() {
		return txnNumber;
	}

	public void setTxnNumber(String txnNumber) {
		this.txnNumber = txnNumber;
	}

	@Override
	public String toString() {
		return "TransactionDetails [txnid=" + txnid + ", description=" + description + ", txndate=" + txndate
				+ ", amount=" + amount + ", modeoftxn=" + modeoftxn + ", refrenceno=" + refrenceno + ", txnfor="
				+ txnfor + ", walletid=" + walletid + ", txntype=" + txntype + ", txnNumber=" + txnNumber + "]";
	}
}
