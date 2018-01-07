package com.logicq.mlm.service.otp;

import com.logicq.mlm.model.sms.ServiceDetails;

public interface IServiceRequestService {
	
	void saveorUpdateRequest(ServiceDetails contentdetails);

}
