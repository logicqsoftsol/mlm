package com.logicq.mlm.service.performance;

import java.util.List;

import com.logicq.mlm.model.performance.UserNetworkCount;
import com.logicq.mlm.vo.NetworkCountVO;

public interface IUserNetworkPerformanceService {
	
	void updateUserNetworkPerformance(UserNetworkCount usernetowrk);
	void addUserNetworkPerformance(UserNetworkCount usernetowrk);
	List<UserNetworkCount> getNetworkPerformance(UserNetworkCount usernetowrk);
	void addUserNetworkPerformanceList(List<UserNetworkCount> usernetowrkList);
    UserNetworkCount getNetworkPerformanceForMemeberandLevel(UserNetworkCount usernetowrk);
    List<NetworkCountVO> getNetworkPerformanceAccordingToUser(String username);
     void saveorupdateUserNetworkPerformance(UserNetworkCount usernetowrk);
}
