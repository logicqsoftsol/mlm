package com.logicq.mlm.vo;

import java.io.Serializable;
import java.util.Date;

public class NetworkVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8191474854874138083L;

	private String memberid;

	private String parentmemberid;

	private String memberlevel;

	private String parentfirstname;
	private String parentlastname;
	
	private Date dateofjoin;

	private String firstname;
	private String lastname;
	

	public String getMemberid() {
		return memberid;
	}


	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}


	public String getParentmemberid() {
		return parentmemberid;
	}


	public void setParentmemberid(String parentmemberid) {
		this.parentmemberid = parentmemberid;
	}


	public String getMemberlevel() {
		return memberlevel;
	}


	public void setMemberlevel(String memberlevel) {
		this.memberlevel = memberlevel;
	}


	public Date getDateofjoin() {
		return dateofjoin;
	}


	public void setDateofjoin(Date dateofjoin) {
		this.dateofjoin = dateofjoin;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getParentfirstname() {
		return parentfirstname;
	}


	public void setParentfirstname(String parentfirstname) {
		this.parentfirstname = parentfirstname;
	}


	public String getParentlastname() {
		return parentlastname;
	}


	public void setParentlastname(String parentlastname) {
		this.parentlastname = parentlastname;
	}


	@Override
	public String toString() {
		return "NetworkVO [memberid=" + memberid + ", parentmemberid=" + parentmemberid + ", memberlevel=" + memberlevel
				+ ", parentfirstname=" + parentfirstname + ", parentlastname=" + parentlastname + ", dateofjoin="
				+ dateofjoin + ", firstname=" + firstname + ", lastname=" + lastname + "]";
	}

}
