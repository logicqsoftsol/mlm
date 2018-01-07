package com.logicq.mlm.dao.performance;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import com.logicq.mlm.dao.AbstractDAO;
import com.logicq.mlm.model.performance.UserPerformance;

@Repository
public class UserPerformanceDAO extends AbstractDAO<UserPerformance> implements IUserPerformanceDAO{

	@Override
	public UserPerformance fetchUserPerformanceAccordingToAggregation(UserPerformance userperformance) {
		userperformance.setAggregationType("Last 1 Week");
		userperformance.setMetting(0);
		userperformance.setNetwork(0);
		userperformance.setRatting(0);
		userperformance.setTask(0);
		userperformance.setPerformancetype("Not Yet");
		userperformance.setIncome(new BigDecimal(00.00));
		return userperformance;
	}

	
}
