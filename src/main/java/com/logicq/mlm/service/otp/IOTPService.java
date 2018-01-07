package com.logicq.mlm.service.otp;

import com.logicq.mlm.model.sms.OTPDetails;

public interface IOTPService {
	
	boolean sendOTP(String mobilenumber);
	
	boolean validateOTPForMobile(int otp,String mobilenumber);
	boolean validateOTPForEMail(int otp,String email);
	
	void sendServiceConfirmation(String mobilenumber,String reasone);
	
	void saveOTPDetails(OTPDetails otpdetails);

}
