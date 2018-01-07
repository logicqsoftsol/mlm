package com.logicq.mlm.dao.performance;

import java.util.List;

import com.logicq.mlm.model.performance.UserNetworkCount;

public interface IUserNetworkPerformanceDAO {

	void updateUserNetworkPerformance(UserNetworkCount usernetowrk);
	void addUserNetworkPerformance(UserNetworkCount usernetowrk);
	List<UserNetworkCount> getNetworkPerformance(UserNetworkCount usernetowrk);
	void addUserNetworkPerformanceList(List<UserNetworkCount> usernetowrkList);
	UserNetworkCount getNetworkPerformanceForMemeberandLevel(UserNetworkCount usernetowrk);
	public void saveorupdateUserNetworkPerformance(UserNetworkCount usernetowrk);
}
