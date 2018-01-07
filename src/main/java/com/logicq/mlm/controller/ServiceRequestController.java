package com.logicq.mlm.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.logicq.mlm.common.factory.LoginFactory;
import com.logicq.mlm.common.helper.sms.MessageHelper;
import com.logicq.mlm.common.vendor.sms.SMSVendor;
import com.logicq.mlm.model.message.EmailDetails;
import com.logicq.mlm.model.profile.UserProfile;
import com.logicq.mlm.model.sms.OTPDetails;
import com.logicq.mlm.model.workflow.WorkFlow;
import com.logicq.mlm.service.messaging.IEmailService;
import com.logicq.mlm.service.otp.IOTPService;
import com.logicq.mlm.service.user.IUserService;
import com.logicq.mlm.service.workflow.IWorkFlowService;
import com.logicq.mlm.vo.LoginVO;
import com.logicq.mlm.vo.UserDetailsVO;

@RestController
@RequestMapping("/api/service")
public class ServiceRequestController {
	

	private static final Logger logger = LoggerFactory
			.getLogger(ServiceRequestController.class);
	
	@Autowired
	IEmailService emailservice;
	
	@Autowired
	IOTPService otpservice;
	
	@Autowired
	IWorkFlowService workflowservice;
    
	@Autowired
	IUserService userservice;

	private final static  SMSVendor smsvendor=SMSVendor.getInstance();
	
	@RequestMapping(value = "/otpSend/{type}", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> sendOTP(@PathVariable String type) throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (!StringUtils.isEmpty(type)) {
					if (type.equalsIgnoreCase("email")) {
						EmailDetails emaildetail = new EmailDetails();
						emaildetail.setSenddate(new Date());
						emaildetail.setSendto(login.getEmail());
						emaildetail.setSubject("OTP For Email Validation");
						emailservice.sendEmailWithOTP(emaildetail);
					}
					if (type.equalsIgnoreCase("MobileNumber")) {
						otpservice.sendOTP(login.getMobilenumber());
					}
				}
			}
		}

		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	

	@RequestMapping(value = "/adminVerificationRequest", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetailsVO> adminVerification() throws Exception {
		UserDetailsVO userdetail=null;
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			EmailDetails emaildetail=new EmailDetails();
			emaildetail.setSenddate(new Date());
			emaildetail.setSendto(smsvendor.getAdminEmail());
			emaildetail.setSubject("Validation Pending For UserName : "+login.getUsername());
			emaildetail.setText(MessageHelper.validationPendingMessageForAdmin(login.getUsername(), login.getEmail(), login.getMobilenumber()));
			emailservice.sendEmail(emaildetail);
		
			//otpservice.sendOTP(otpdetails.getMobilenumber());
		
			}
		}
	
		return new ResponseEntity<UserDetailsVO>(userdetail, HttpStatus.OK);
	}

	@RequestMapping(value = "/otpValidate/{type}/{otpnumber}", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetailsVO> validateOTP(@PathVariable String type,@PathVariable Integer otpnumber)
			throws Exception {
		UserDetailsVO userdetails = new UserDetailsVO();
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				UserProfile userprofile = new UserProfile();
				boolean otpverified=false;
				if (!StringUtils.isEmpty(type)) {
					if (type.equalsIgnoreCase("MobileNumber")) {
						otpverified=otpservice.validateOTPForMobile(otpnumber, login.getMobilenumber());
					}
					if(type.equalsIgnoreCase("email")){
						otpverified=otpservice.validateOTPForEMail(otpnumber, login.getEmail());
					}
					if(otpverified){
					userprofile.setLogindetails(LoginFactory.createLoginDetails(login));
					userprofile=userservice.fetchUser(userprofile);
					List<WorkFlow> workflowlist = workflowservice.getPendingWorkFlowForUser(login.getUsername(),
							String.valueOf(userprofile.getId()));
					for (WorkFlow work : workflowlist) {
						if (type.equalsIgnoreCase("MobileNumber") && (work.getWorktype().equalsIgnoreCase("MOBILE_VERIFICATION"))) {
							userdetails.setMobilenoVerified(true);
							work.setStatus(true);
							workflowservice.updateWorkFlow(work);
						}
						if (type.equalsIgnoreCase("email") && (work.getWorktype().equalsIgnoreCase("EMAIL_VERIFICATION"))) {							userdetails.setMobilenoVerified(true);
						    userdetails.setEmailVerified(true);	
					    	work.setStatus(true);
					    	workflowservice.updateWorkFlow(work);
						}
					}
					
				}
					}
			}
		}
		return new ResponseEntity<UserDetailsVO>(userdetails, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/otpValidateForEmail", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetailsVO> validateOTPForEmail(@RequestBody OTPDetails otpdetails) throws Exception {
		UserDetailsVO userdetails=new UserDetailsVO();
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				UserProfile userprofile = new UserProfile();
				if (otpservice.validateOTPForEMail(otpdetails.getOtpnumber(), otpdetails.getReciveremailid())) {
					userprofile.setLogindetails(LoginFactory.createLoginDetails(login));
					userservice.fetchUser(userprofile);
					List<WorkFlow> workflowlist = workflowservice.getPendingWorkFlowForUser(login.getUsername(),
							String.valueOf(userprofile.getId()));
					for (WorkFlow work : workflowlist) {
						if (work.getWorktype().equalsIgnoreCase("EMAIL_VERIFICATION")) {
							userdetails.setEmailVerified(true);
							work.setStatus(true);
							workflowservice.updateWorkFlow(work);
						}
					}
				}
			}
		}
		
		return new ResponseEntity<UserDetailsVO>(userdetails, HttpStatus.OK);
	}

}
