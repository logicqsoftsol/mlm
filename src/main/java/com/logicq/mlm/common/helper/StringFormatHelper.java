package com.logicq.mlm.common.helper;

import java.security.SecureRandom;
import java.util.Random;
import java.util.StringTokenizer;

import org.springframework.util.StringUtils;

public class StringFormatHelper {

	private static final String SMS_MESSAGE_FORMAT_FOR_VENDOR = "%20";
	private static  Random random = new SecureRandom();
	private static char[] characterSet="ABCDEFGHIJK0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static Random rannumber = new Random();
	public static String formatStringForSMS(String message) {
		StringBuilder smsMsgString = new StringBuilder();
		StringTokenizer tokens = new StringTokenizer(message);
		while (tokens.hasMoreTokens()) {
			smsMsgString.append(tokens.nextToken().trim() + SMS_MESSAGE_FORMAT_FOR_VENDOR);
		}
		return smsMsgString.toString();
	}

	
	public static String randomString() {
	    char[] result = new char[8];
	    for (int i = 0; i < result.length; i++) {
	        // picks a random index out of character set > random character
	        int randomCharIndex = random.nextInt(characterSet.length);
	        result[i] = characterSet[randomCharIndex];
	    }
	    return new String(result);
	}
	
	public static String randomNumber() {
	    return String.valueOf(rannumber.nextInt(6));
	}
	
	public static String sevenDigitRandomNumber() {
	    return String.valueOf( rannumber.nextInt(90000000) + 10000000);
	}
	
	public static Integer getLevelAsInteger(String level) {
		if (!StringUtils.isEmpty(level)) {
			String levelint = level.replace("LEVEL", "").trim();
			return Integer.valueOf(levelint);
		}
		return null;
	}
	
	public static String getLevelAsString(Integer level) {
		return "LEVEL"+level;
	}
}
