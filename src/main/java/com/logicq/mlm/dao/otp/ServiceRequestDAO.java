package com.logicq.mlm.dao.otp;

import org.springframework.stereotype.Repository;

import com.logicq.mlm.dao.AbstractDAO;
import com.logicq.mlm.model.sms.ServiceDetails;

@Repository
public class ServiceRequestDAO extends AbstractDAO<ServiceDetails> implements IServiceRequestDAO{

	@Override
	public void saveOrUpdateService(ServiceDetails contentdetails) {
	  saveOrUpdate(contentdetails);
	}

}
