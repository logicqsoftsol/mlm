package com.logicq.mlm.service.performance;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logicq.mlm.dao.performance.UserPerformanceDAO;
import com.logicq.mlm.model.performance.UserPerformance;

@Service
@Transactional
public class UserPerformanceService  implements IUserPerformanceService{

	@Autowired
	UserPerformanceDAO userperformancedao;

	@Override
	public UserPerformance fetchUserPerformanceAccordingToAggregation(UserPerformance userperformance) {
		return userperformancedao.fetchUserPerformanceAccordingToAggregation(userperformance);
	}
	
	

}
