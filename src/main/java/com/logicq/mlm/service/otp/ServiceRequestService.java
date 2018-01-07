package com.logicq.mlm.service.otp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.mlm.dao.otp.IServiceRequestDAO;
import com.logicq.mlm.model.sms.ServiceDetails;

@Service
@Transactional
public class ServiceRequestService implements IServiceRequestService {

	@Autowired
	IServiceRequestDAO contentmodification;
	
	
	@Override
	public void saveorUpdateRequest(ServiceDetails contentdetails) {
		contentmodification.saveOrUpdateService(contentdetails);	
	}

}
