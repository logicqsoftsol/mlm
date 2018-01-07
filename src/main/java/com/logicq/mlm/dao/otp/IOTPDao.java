package com.logicq.mlm.dao.otp;

import com.logicq.mlm.model.sms.OTPDetails;

public interface IOTPDao {
	
	void saveOrUpdateOTPDetails(OTPDetails otpdetails);
	
	boolean validateOTPForMobileNumber(int otp,String mobilenumber);
	 boolean validateOTPForEmail(int opt, String email) ;

}
