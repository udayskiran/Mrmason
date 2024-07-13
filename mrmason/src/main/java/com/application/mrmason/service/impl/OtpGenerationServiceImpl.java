package com.application.mrmason.service.impl;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mrmason.service.EmailService;
import com.application.mrmason.service.OtpGenerationService;

@Service
public class OtpGenerationServiceImpl implements OtpGenerationService {
	@Autowired
	EmailService mailService;

	LocalTime local = LocalTime.now();
	@Autowired
	SmsService smsService;
	private final Map<String, String> otpStorage = new HashMap<>(); // Store OTPs temporarily

	// Generate and send OTP to the user (via email, SMS, etc.)
	@Override
	public String generateOtp(String mail) {
		int randomNum = (int) (Math.random() * 900000) + 100000;
		String otp = String.valueOf(randomNum);
		otpStorage.put(mail, otp);

		mailService.sendEmail(mail, otp);

		return otp;
	}

	@Override
	public boolean verifyOtp(String email, String enteredOtp) {
		String storedOtp = otpStorage.get(email);
		return storedOtp != null && storedOtp.equals(enteredOtp);
	}

	@Override
	public String generateMobileOtp(String mobile) {
		int randomNum = (int) (Math.random() * 900000) + 100000;
		String otp = String.valueOf(randomNum);
		otpStorage.put(mobile, otp);
		smsService.sendSMSMessage(mobile, otp);
		return otp;
	}

	@Override
	public boolean verifyMobileOtp(String mobile, String enteredOtp) {
		String storedOtp = otpStorage.get(mobile);
		return storedOtp != null && storedOtp.equals(enteredOtp);
	}

}