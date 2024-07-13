package com.application.mrmason.service;

import com.application.mrmason.entity.CustomerEmailOtp;

public interface CustomerEmailOtpService {

	String isEmailExists(String email);
	CustomerEmailOtp updateData(String otp,String email);
	
}
