package com.logicq.mlm.model.message;

import java.util.Date;
import java.util.List;

public class EmailDetails {
	
	private String sendto;
	private String sendfrom;
	private Date senddate;
	private String text;
	private String subject;
	private String sendstatus;
	
	private List<String> multiplesendto;

	public String getSendto() {
		return sendto;
	}

	public void setSendto(String sendto) {
		this.sendto = sendto;
	}

	public String getSendfrom() {
		return sendfrom;
	}

	public void setSendfrom(String sendfrom) {
		this.sendfrom = sendfrom;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSendstatus() {
		return sendstatus;
	}

	public void setSendstatus(String sendstatus) {
		this.sendstatus = sendstatus;
	}

	public List<String> getMultiplesendto() {
		return multiplesendto;
	}

	public void setMultiplesendto(List<String> multiplesendto) {
		this.multiplesendto = multiplesendto;
	}

	public Date getSenddate() {
		return senddate;
	}

	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}

	@Override
	public String toString() {
		return "EmailDetails [sendto=" + sendto + ", sendfrom=" + sendfrom + ", senddate=" + senddate + ", text=" + text
				+ ", subject=" + subject + ", sendstatus=" + sendstatus + ", multiplesendto=" + multiplesendto + "]";
	}

	
	
}
