package com.logicq.mlm.dao.otp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.mlm.dao.AbstractDAO;
import com.logicq.mlm.model.sms.OTPDetails;

@Repository
public class OTPDao extends AbstractDAO<OTPDetails> implements IOTPDao  {

	@Override
	public void saveOrUpdateOTPDetails(OTPDetails otpdetails) {
		saveOrUpdate(otpdetails);
	}

	@Override
	public boolean validateOTPForMobileNumber(int opt, String mobilenumber) {
		String sql = " from OTPDetails where mobilenumber= '" + mobilenumber + "'" + " and otpnumber=" + opt;
		List<OTPDetails> otpdetails = (List<OTPDetails>) execcuteQuery(sql);
		return otpdetails.size() == 1;
	}
	
	@Override
	public boolean validateOTPForEmail(int opt, String email) {
		String sql = " from OTPDetails where reciveremailid= '" + email + "'" + " and otpnumber=" + opt;
		List<OTPDetails> otpdetails = (List<OTPDetails>) execcuteQuery(sql);
		return otpdetails.size() == 1;
	}

}
