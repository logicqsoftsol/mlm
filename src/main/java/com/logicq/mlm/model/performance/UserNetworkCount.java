package com.logicq.mlm.model.performance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_NETWORK_COUNT")
public class UserNetworkCount {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column(name = "MEMBER_ID", nullable = false)
	private String memberid;
	
	@Column(name = "PARENT_ID", nullable = false)
	private String parentid;
	

	@Column(name = "LEVEL",  nullable = false)
	private Integer networklevel;
	
	@Column(name = "TOTAL_MEMEBER_COUNT", nullable = false)
	private Integer membercount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public Integer getNetworklevel() {
		return networklevel;
	}

	public void setNetworklevel(Integer networklevel) {
		this.networklevel = networklevel;
	}

	public Integer getMembercount() {
		return membercount;
	}

	public void setMembercount(Integer membercount) {
		this.membercount = membercount;
	}

	@Override
	public String toString() {
		return "UserNetworkCount [id=" + id + ", memberid=" + memberid + ", parentid=" + parentid + ", networklevel="
				+ networklevel + ", membercount=" + membercount + "]";
	}
	
	
}
