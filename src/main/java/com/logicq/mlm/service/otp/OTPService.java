package com.logicq.mlm.service.otp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.logicq.mlm.common.helper.sms.OTPGenerationHelper;
import com.logicq.mlm.common.helper.sms.SMSHelper;
import com.logicq.mlm.common.helper.sms.MessageHelper;
import com.logicq.mlm.dao.otp.IOTPDao;
import com.logicq.mlm.model.sms.OTPDetails;
import com.logicq.mlm.model.sms.SMSDetails;

@Service
@Transactional
public class OTPService implements IOTPService{
   
	@Autowired
	IOTPDao otpdao;
	
	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean sendOTP(String mobilenumber) {
		final int otp=OTPGenerationHelper.generateOTP();
		OTPDetails otpdetails=new OTPDetails();
		otpdetails.setMobilenumber(mobilenumber);
		otpdetails.setOtpnumber(otp);
		otpdetails.setOtpvalidate(false);
		otpdetails.setOtpvalidationdate(null);
		otpdao.saveOrUpdateOTPDetails(otpdetails);
		
		SMSDetails smsdetails=new SMSDetails();
		smsdetails.setMessage(MessageHelper.generateOTPMessage(otp));
		smsdetails.setMobilenumber(mobilenumber);
		return SMSHelper.sendSMS(smsdetails);

	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean validateOTPForMobile(int otp, String mobilenumber) {
		return otpdao.validateOTPForMobileNumber(otp, mobilenumber);
	}
	
	
	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean validateOTPForEMail(int otp, String email) {
		return otpdao.validateOTPForEmail(otp, email);
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void sendServiceConfirmation(String mobilenumber, String reasone) {
		SMSDetails smsdetailscust=new SMSDetails();
		smsdetailscust.setMessage(MessageHelper.generateMessageForServiceConfirmation(reasone));
		smsdetailscust.setMobilenumber(mobilenumber);
		
		SMSDetails smsdetailsadmin=new SMSDetails();
		smsdetailsadmin.setMessage(MessageHelper.generateMessageForAdmin(mobilenumber, reasone));
		smsdetailsadmin.setMobilenumber(MessageHelper.getAdminMobileNumber());
		
		SMSHelper.sendSMS(smsdetailscust);
		SMSHelper.sendSMS(smsdetailsadmin);
	}

	@Override
	public void saveOTPDetails(OTPDetails otpdetails) {
		otpdao.saveOrUpdateOTPDetails(otpdetails);
	}

}
