package com.logicq.mlm.vo;

import java.io.Serializable;

public class StatusVO extends WalletStmntVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4577307421904701614L;
	
	private boolean mobilenoVerified;
	private boolean emailVerified;
	private boolean adminVerified;
	public boolean isMobilenoVerified() {
		return mobilenoVerified;
	}
	public void setMobilenoVerified(boolean mobilenoVerified) {
		this.mobilenoVerified = mobilenoVerified;
	}
	public boolean isEmailVerified() {
		return emailVerified;
	}
	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}
	public boolean isAdminVerified() {
		return adminVerified;
	}
	public void setAdminVerified(boolean adminVerified) {
		this.adminVerified = adminVerified;
	}
	@Override
	public String toString() {
		return "StatusVO [mobilenoVerified=" + mobilenoVerified + ", emailVerified=" + emailVerified
				+ ", adminVerified=" + adminVerified + "]";
	}
	 
}
