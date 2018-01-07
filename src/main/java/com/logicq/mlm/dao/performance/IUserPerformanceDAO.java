package com.logicq.mlm.dao.performance;

import com.logicq.mlm.model.performance.UserPerformance;

public interface IUserPerformanceDAO {
	
	UserPerformance fetchUserPerformanceAccordingToAggregation(UserPerformance userperformance);

}
