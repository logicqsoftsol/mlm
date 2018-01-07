package com.logicq.mlm.common.helper.sms;

import java.math.BigDecimal;
import java.util.Date;

public class MessageHelper {

	public static String generateOTPMessage(int otp) {

		return " Your OTP is " + otp + "\n" + "\n" + " From GETPAY.CO.IN " + "\n" + "";
	}

	public static String generateMessageForServiceConfirmation(String servicename) {

		return " Thanks for your " + servicename + " service request We will get back to you soon" + "\n" + "\n"
				+ " Your Service Patner " + "\n" + "HomePlus Service ";
	}

	public static String generateMessageForAdmin(String mobilenumber, String servicename) {
		return " Customer " + mobilenumber + " request for service " + servicename;
	}

	public static String getAdminMobileNumber() {
		return "08763621986";
	}
	
	public static String getAdminEmail() {
		return "getpayindia@gmail.com";
	}

	public static String generateUserRegistartionMessage(String username, String password, String fname, String lname) {
		return " Welcome to GetPay Mr " + fname + " Your login details USERNAME " + username + " PASSWORD "
				+ password;
	}

	public static String generateUserRegistartionMessageForAdmin(String username, String emailid) {
		return "The User with USERNAME :" + username + " has been registated at "
				+ new Date();
	}

	public static String validationPendingMessageForAdmin(String username, String emailid, String mobilenumber) {
		return "The User with USERNAME :" + username + " , " + " EMAIL :" + emailid + " , " + " Mobile Number :"
				+ mobilenumber + " has pending for approval";
	}
	
	public static String generateAmountEncashMessageForAdmin(String username, String mobilenumber, BigDecimal encashamount) {
		return "The User with USERNAME :" + username + " and" + " Mobile Number :" + mobilenumber + " has been request for Encashing Amount INR "+encashamount+"on Date "+new Date();
	}
	
	public static String generateAmountEncashMessageForUser(String username, String mobilenumber, BigDecimal encashamount) {
		return "Your have sucessfully request for encashing  INR "+encashamount+" on Date "+new Date();
	}
}
