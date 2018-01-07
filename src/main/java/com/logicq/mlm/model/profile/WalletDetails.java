package com.logicq.mlm.model.profile;

import java.io.Serializable;
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

@Entity
@Table(name = "WALLET_DETAILS")
public class WalletDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8085100318892191287L;

	@Id
	@Column(name = "WALLET_ID")
	private String walletid;

	@Column(name = "WALLET_NUMBER")
	private String walletnumber;

	@Column(name = "IS_ACTIVE")
	private boolean isactive;

	@Temporal(TemporalType.DATE)
	@Column(name = "ACTIVE_DATE")
	private Date walletactivedate;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "PROFILE_ID")
	private UserProfile userprofile;

   // @OneToOne(mappedBy = "wallet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private WalletStatement walletStatement;

	public String getWalletid() {
		return walletid;
	}

	public void setWalletid(String walletid) {
		this.walletid = walletid;
	}

	public String getWalletnumber() {
		return walletnumber;
	}

	public void setWalletnumber(String walletnumber) {
		this.walletnumber = walletnumber;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public Date getWalletactivedate() {
		return walletactivedate;
	}

	public void setWalletactivedate(Date walletactivedate) {
		this.walletactivedate = walletactivedate;
	}

	public UserProfile getUserprofile() {
		return userprofile;
	}

	public void setUserprofile(UserProfile userprofile) {
		this.userprofile = userprofile;
	}

	@Override
	public String toString() {
		return "WalletDetails [walletid=" + walletid + ", walletnumber=" + walletnumber + ", isactive=" + isactive
				+ ", walletactivedate=" + walletactivedate + "]";
	}

	
//	public WalletStatement getWalletStatement() {
//		return walletStatement;
//	}
//
//	public void setWalletStatement(WalletStatement walletStatement) {
//		this.walletStatement = walletStatement;
//	}


}
