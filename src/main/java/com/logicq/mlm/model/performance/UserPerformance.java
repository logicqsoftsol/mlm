package com.logicq.mlm.model.performance;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_PERFORMANCE")
public class UserPerformance {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NETWORK_COUNT")
	private Integer network;
	
	@Column(name = "MEETING_COUNT")
	private Integer metting;
	
	@Column(name = "TASK_COUNT")
	private Integer task;
	
	@Column(name = "TOTAL_INCOME")
	private BigDecimal income;
	
	@Column(name = "PERFORMANCE_TYPE")
	private String performancetype;
	
	@Column(name = "RATTING")
	private int ratting;
	
	@Column(name = "AGGREGRATION_TYPE")
	private String aggregationType;
	
	
	@Column(name = "USER_ID")
	private String userid;

	public Integer getNetwork() {
		return network;
	}

	public void setNetwork(Integer network) {
		this.network = network;
	}

	public Integer getMetting() {
		return metting;
	}

	public void setMetting(Integer metting) {
		this.metting = metting;
	}

	public Integer getTask() {
		return task;
	}

	public void setTask(Integer task) {
		this.task = task;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public String getPerformancetype() {
		return performancetype;
	}

	public void setPerformancetype(String performancetype) {
		this.performancetype = performancetype;
	}

	public int getRatting() {
		return ratting;
	}

	public void setRatting(int ratting) {
		this.ratting = ratting;
	}

	public String getAggregationType() {
		return aggregationType;
	}

	public void setAggregationType(String aggregationType) {
		this.aggregationType = aggregationType;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserPerformance [id=" + id + ", network=" + network + ", metting=" + metting + ", task=" + task
				+ ", income=" + income + ", performancetype=" + performancetype + ", ratting=" + ratting
				+ ", aggregationType=" + aggregationType + ", userid=" + userid + "]";
	}


	
	
}
