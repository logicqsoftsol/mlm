package com.logicq.mlm.model.workflow;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.logicq.mlm.common.BaseEntity;

@Entity
@Table(name = "WORK_FLOW")
public class WorkFlow  extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2824402301798535966L;

	
	@Id
	@Column(name = "WORK_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long workid;

	@Column(name = "WORK_TYPE")
	private String worktype;

	@Column(name = "STATUS")
	private Boolean status;

	@Column(name = "MESSAGE")
	private String message;

	@Column(name = "PROFILE_ID")
	private String profileid;
	
	@Column(name = "ASSIGNED_TO")
	private String assignedto;
	
	@Lob
	@Column(name = "WORK_PARAMETER")
	private byte[] workparameter;

	

	public String getWorktype() {
		return worktype;
	}

	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getProfileid() {
		return profileid;
	}

	public void setProfileid(String profileid) {
		this.profileid = profileid;
	}

	public String getAssignedto() {
		return assignedto;
	}

	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}

	public Long getWorkid() {
		return workid;
	}

	public void setWorkid(Long workid) {
		this.workid = workid;
	}

	public byte[] getWorkparameter() {
		return workparameter;
	}

	public void setWorkparameter(byte[] workparameter) {
		this.workparameter = workparameter;
	}

	
	@Override
	public String toString() {
		return "WorkFlow [workid=" + workid + ", worktype=" + worktype + ", status=" + status + ", message=" + message
				+ ", profileid=" + profileid + ", assignedto=" + assignedto + ", workparameter="
				+ Arrays.toString(workparameter) + "]";
	}

	

	


}
