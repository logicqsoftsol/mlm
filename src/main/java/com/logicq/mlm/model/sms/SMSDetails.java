package com.logicq.mlm.model.sms;

public class SMSDetails {
	
	private String mobilenumber;
	private String msgreasone;
	private String message;
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getMsgreasone() {
		return msgreasone;
	}
	public void setMsgreasone(String msgreasone) {
		this.msgreasone = msgreasone;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	@Override
	public String toString() {
		return "SMSDetails [mobilenumber=" + mobilenumber + ", msgreasone=" + msgreasone + ", message=" + message + "]";
	}

	
	
}
