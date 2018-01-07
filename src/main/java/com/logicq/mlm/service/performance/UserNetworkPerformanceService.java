package com.logicq.mlm.service.performance;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logicq.mlm.common.helper.StringFormatHelper;
import com.logicq.mlm.dao.performance.IUserNetworkPerformanceDAO;
import com.logicq.mlm.model.performance.UserNetworkCount;
import com.logicq.mlm.vo.NetworkCountVO;

@Service
@Transactional
public class UserNetworkPerformanceService  implements IUserNetworkPerformanceService{

	@Autowired
	IUserNetworkPerformanceDAO networkperfdao;
	
	@Override
	public void updateUserNetworkPerformance(UserNetworkCount usernetowrk) {
		networkperfdao.updateUserNetworkPerformance(usernetowrk);
		
	}

	@Override
	public void addUserNetworkPerformance(UserNetworkCount usernetowrk) {
		networkperfdao.addUserNetworkPerformance(usernetowrk);
		
	}

	@Override
	public List<UserNetworkCount> getNetworkPerformance(UserNetworkCount usernetowrk) {
		return networkperfdao.getNetworkPerformance(usernetowrk);
	}

	
	@Override
	public List<NetworkCountVO> getNetworkPerformanceAccordingToUser(String username) {
		UserNetworkCount usernetworkcount=new UserNetworkCount();
		usernetworkcount.setMemberid(username);
		List<NetworkCountVO> networkCountVOList=new ArrayList<>();
		List<UserNetworkCount> networkCountList= networkperfdao.getNetworkPerformance(usernetworkcount);
		if (null != networkCountList && !networkCountList.isEmpty()) {
			for (UserNetworkCount networkCount : networkCountList) {
				NetworkCountVO networkCountVO = new NetworkCountVO();
				networkCountVO.setCount(networkCount.getMembercount());
				networkCountVO.setLevel(StringFormatHelper.getLevelAsString(networkCount.getNetworklevel()));
				networkCountVOList.add(networkCountVO);
			}
		}
	  return networkCountVOList;
	}
	
	@Override
	public void addUserNetworkPerformanceList(List<UserNetworkCount> usernetowrkList) {
		networkperfdao.addUserNetworkPerformanceList(usernetowrkList);
		
	}

	@Override
	public UserNetworkCount getNetworkPerformanceForMemeberandLevel(UserNetworkCount usernetowrk) {
		return networkperfdao.getNetworkPerformanceForMemeberandLevel(usernetowrk);
	}

	@Override
	public void saveorupdateUserNetworkPerformance(UserNetworkCount usernetowrk) {
		networkperfdao.saveorupdateUserNetworkPerformance(usernetowrk);
	}

}
