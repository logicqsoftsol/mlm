package com.logicq.mlm.model.profile;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "NETWORK_INFO")
public class NetworkInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6382790028584606455L;

	@Id
	@Column(name = "MEMBER_ID" ,nullable = false)
	private String memberid;

	@Column(name = "PARENT_ID",nullable = false)
	private String parentmemberid;

	@Column(name = "MEMEBR_LEVEL",nullable = false)
	private String memberlevel;

	@Temporal(TemporalType.DATE)
	@Column(name = "JOIN_DATE", nullable = true)
	private Date dateofjoin;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "PROFILE_ID")
	private UserProfile userprofile;
	
	
	@Column(name = "IS_UPDATE", nullable = false)
	private Boolean isUpdate;
	
	@Lob
	@Column(name = "NETWORK_JSON")
	private byte[] networkjson;
	
	

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

	public UserProfile getUserprofile() {
		return userprofile;
	}

	public void setUserprofile(UserProfile userprofile) {
		this.userprofile = userprofile;
	}
	public byte[] getNetworkjson() {
		return networkjson;
	}

	public void setNetworkjson(byte[] networkjson) {
		this.networkjson = networkjson;
	}

	public Boolean getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	@Override
	public String toString() {
		return "NetworkInfo [memberid=" + memberid + ", parentmemberid=" + parentmemberid + ", memberlevel="
				+ memberlevel + ", dateofjoin=" + dateofjoin + ", isUpdate=" + isUpdate + ", networkjson="
				+ Arrays.toString(networkjson) + "]";
	}
}
