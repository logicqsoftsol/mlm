package com.logicq.mlm.common.helper.sms;

import java.util.Random;

public class OTPGenerationHelper {
	private static int max=99999;
	private static int min=1000;
	
	public static int generateOTP(){
		Random random=new Random();
		return randInt(random,min,max);
	}

	private static int randInt(Random rand,int min, int max) {
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
}
