package com.logicq.mlm.vo;

import java.io.Serializable;

public class NetworkCountVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1560824831605913118L;
	
	private String level;
	private int count;
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
	@Override
	public String toString() {
		return "NetworkCountVO [level=" + level + ", count=" + count + "]";
	}

}
