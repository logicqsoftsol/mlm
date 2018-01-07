package com.logicq.mlm.dao.otp;

import com.logicq.mlm.model.sms.ServiceDetails;

public interface IServiceRequestDAO {
	
	void saveOrUpdateService(ServiceDetails contentdetails);

}
