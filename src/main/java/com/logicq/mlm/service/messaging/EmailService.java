package com.logicq.mlm.service.messaging;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.logicq.mlm.common.helper.sms.MessageHelper;
import com.logicq.mlm.common.helper.sms.OTPGenerationHelper;
import com.logicq.mlm.common.vendor.sms.SMSVendor;
import com.logicq.mlm.model.message.EmailDetails;
import com.logicq.mlm.model.sms.OTPDetails;
import com.logicq.mlm.service.otp.IOTPService;
import com.logicq.mlm.service.otp.OTPService;



@Service
@Transactional
public class EmailService implements IEmailService{

	@Autowired
	MailSender  emailsender;
	
	@Autowired
	IOTPService otpservice;
	
	private final static  SMSVendor smsvendor=SMSVendor.getInstance();
	
	@Override
	public boolean sendEmailWithOTP(EmailDetails emaildetails) throws Exception {
		OTPDetails otpdetail=new OTPDetails();
		SimpleMailMessage emailMsg = new SimpleMailMessage();
		int otp = OTPGenerationHelper.generateOTP();
		//emaildetails.setSubject("OTP For Email Validation");
		emaildetails.setText(MessageHelper.generateOTPMessage(otp));
		emaildetails.setSendfrom(smsvendor.getAdminEmail());
		emailMsg.setFrom(emaildetails.getSendfrom());
		emailMsg.setTo(emaildetails.getSendto());
		emailMsg.setSubject(emaildetails.getSubject());
		emailMsg.setText(emaildetails.getText());
		
		otpdetail.setReciveremailid(emaildetails.getSendto());
		otpdetail.setOtpnumber(otp);
		otpdetail.setOtpvalidate(false);
		otpdetail.setOtpvalidationdate(null);
		otpdetail.setSenderemailid(emaildetails.getSendfrom());
		otpservice.saveOTPDetails(otpdetail);
		
		
		emailsender.send(emailMsg);
		return true;
	}

	@Override
	public boolean sendEmail(EmailDetails emaildetails) throws Exception {
		SimpleMailMessage emailMsg = new SimpleMailMessage();
		emailMsg.setFrom(emaildetails.getSendfrom());
		emailMsg.setTo(emaildetails.getSendto());
		emailMsg.setSubject(emaildetails.getSubject());
		emailMsg.setText(emaildetails.getText());
		emailsender.send(emailMsg);
		return false;
	}

}
