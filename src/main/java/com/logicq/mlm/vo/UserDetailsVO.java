
package com.logicq.mlm.vo;

import java.util.List;

import com.logicq.mlm.model.admin.TaskDetails;
import com.logicq.mlm.model.performance.UserPerformance;
import com.logicq.mlm.model.profile.NetWorkDetails;
import com.logicq.mlm.model.profile.NetworkInfo;
import com.logicq.mlm.model.profile.UserDocument;
import com.logicq.mlm.model.profile.UserProfile;
import com.logicq.mlm.model.wallet.WalletStatement;

public class UserDetailsVO extends StatusVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 369460436082862799L;

	private UserProfile userprofile;
	
	private WalletStatement walletStatement;
	
	private UserPerformance userperformance;
	
	private NetWorkDetails networkjson;
	
	private List<TaskDetails> tasklist;
	
	private UserDocument document;
	
	private List<NetworkVO> networkinfolist;
	
	private List<NetworkCountVO> networkcountlist;

	public UserProfile getUserprofile() {
		return userprofile;
	}

	public void setUserprofile(UserProfile userprofile) {
		this.userprofile = userprofile;
	}

	public WalletStatement getWalletStatement() {
		return walletStatement;
	}

	public void setWalletStatement(WalletStatement walletStatement) {
		this.walletStatement = walletStatement;
	}

	public UserPerformance getUserperformance() {
		return userperformance;
	}

	public void setUserperformance(UserPerformance userperformance) {
		this.userperformance = userperformance;
	}

	public NetWorkDetails getNetworkjson() {
		return networkjson;
	}

	public void setNetworkjson(NetWorkDetails networkjson) {
		this.networkjson = networkjson;
	}

	public List<TaskDetails> getTasklist() {
		return tasklist;
	}

	public void setTasklist(List<TaskDetails> tasklist) {
		this.tasklist = tasklist;
	}

	public UserDocument getDocument() {
		return document;
	}

	public void setDocument(UserDocument document) {
		this.document = document;
	}

	public List<NetworkVO> getNetworkinfolist() {
		return networkinfolist;
	}

	public void setNetworkinfolist(List<NetworkVO> networkinfolist) {
		this.networkinfolist = networkinfolist;
	}

	public List<NetworkCountVO> getNetworkcountlist() {
		return networkcountlist;
	}

	public void setNetworkcountlist(List<NetworkCountVO> networkcountlist) {
		this.networkcountlist = networkcountlist;
	}

	@Override
	public String toString() {
		return "UserDetailsVO [userprofile=" + userprofile + ", walletStatement=" + walletStatement
				+ ", userperformance=" + userperformance + ", networkjson=" + networkjson + ", tasklist=" + tasklist
				+ ", document=" + document + ", networkinfolist=" + networkinfolist + ", networkcountlist="
				+ networkcountlist + "]";
	}

}
