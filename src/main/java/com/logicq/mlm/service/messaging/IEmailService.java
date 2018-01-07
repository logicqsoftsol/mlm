package com.logicq.mlm.service.messaging;

import com.logicq.mlm.model.message.EmailDetails;

public interface IEmailService {
	
	boolean sendEmailWithOTP(EmailDetails emaildetails) throws Exception;
	
	boolean sendEmail(EmailDetails emaildetails) throws Exception;

}
