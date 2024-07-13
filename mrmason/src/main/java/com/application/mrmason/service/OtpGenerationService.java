package com.application.mrmason.service;

public interface OtpGenerationService {
	
	String generateOtp(String mail);
	boolean verifyOtp(String email, String enteredOtp);
	String generateMobileOtp(String mobile);
	boolean verifyMobileOtp(String mobile, String enteredOtp);
}
