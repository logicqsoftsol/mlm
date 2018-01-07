package com.logicq.mlm.service.performance;

import com.logicq.mlm.model.performance.UserPerformance;

public interface IUserPerformanceService {

	UserPerformance fetchUserPerformanceAccordingToAggregation(UserPerformance userperformance);
	
}
