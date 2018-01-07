package com.logicq.mlm.model.admin;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "NETWORK_TASK")
public class NetWorkTask implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8451718792611459286L;

	@Id
	@Column(name = "MEMBER_ID" ,nullable = false)
	private String memberid;
	
	@Column(name = "PARENT_ID" ,nullable = false)
	private String parentid;
  
	@Column(name = "TASK_STATUS" ,nullable = false)
	private Boolean taskStatus;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATION_DATE", nullable = true)
	private Date taskcreationdate;

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

	public Boolean getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Boolean taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Date getTaskcreationdate() {
		return taskcreationdate;
	}

	public void setTaskcreationdate(Date taskcreationdate) {
		this.taskcreationdate = taskcreationdate;
	}

	@Override
	public String toString() {
		return "NetWorkTask [memberid=" + memberid + ", parentid=" + parentid + ", taskStatus=" + taskStatus
				+ ", taskcreationdate=" + taskcreationdate + "]";
	}
	
	
}
