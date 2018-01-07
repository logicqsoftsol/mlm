package com.logicq.mlm.model.admin;

import java.util.Date;

import com.logicq.mlm.vo.EncashVO;

public class TaskDetails {
	
	private String taskname;
	private String taskfor;
	private Date taskassigneddate;
	private String tasktype;
	private String taskstatus;
	private String priority;
	private Long taskid;
	private String message;
	
	private EncashVO encashvo;
	
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getTaskfor() {
		return taskfor;
	}
	public void setTaskfor(String taskfor) {
		this.taskfor = taskfor;
	}
	public Date getTaskassigneddate() {
		return taskassigneddate;
	}
	public void setTaskassigneddate(Date taskassigneddate) {
		this.taskassigneddate = taskassigneddate;
	}
	public String getTasktype() {
		return tasktype;
	}
	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}
	public String getTaskstatus() {
		return taskstatus;
	}
	public void setTaskstatus(String taskstatus) {
		this.taskstatus = taskstatus;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Long getTaskid() {
		return taskid;
	}
	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}
	public EncashVO getEncashvo() {
		return encashvo;
	}
	public void setEncashvo(EncashVO encashvo) {
		this.encashvo = encashvo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "TaskDetails [taskname=" + taskname + ", taskfor=" + taskfor + ", taskassigneddate=" + taskassigneddate
				+ ", tasktype=" + tasktype + ", taskstatus=" + taskstatus + ", priority=" + priority + ", taskid="
				+ taskid + ", message=" + message + ", encashvo=" + encashvo + "]";
	}
	
	

}
