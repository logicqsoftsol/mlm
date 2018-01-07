package com.logicq.mlm.vo;

import java.io.Serializable;

public class PasswordVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4019734399821601762L;
	
	private String oldpassword;
	private String newPassword;
	private String confirmPasword;
	private String date;
	private String mobilenumber;
	private String gpmidno;
	private String cgpmidno;
	private String username;
	
	
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPasword() {
		return confirmPasword;
	}
	public void setConfirmPasword(String confirmPasword) {
		this.confirmPasword = confirmPasword;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getGpmidno() {
		return gpmidno;
	}
	public void setGpmidno(String gpmidno) {
		this.gpmidno = gpmidno;
	}
	public String getCgpmidno() {
		return cgpmidno;
	}
	public void setCgpmidno(String cgpmidno) {
		this.cgpmidno = cgpmidno;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	@Override
	public String toString() {
		return "PasswordVO [oldpassword=" + oldpassword + ", newPassword=" + newPassword + ", confirmPasword="
				+ confirmPasword + ", date=" + date + ", mobilenumber=" + mobilenumber + ", gpmidno=" + gpmidno
				+ ", cgpmidno=" + cgpmidno + ", username=" + username + "]";
	}
	
	
}
