package com.application.mrmason.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mrmason.entity.CustomerEmailOtp;
import com.application.mrmason.repository.CustomerEmailOtpRepo;
import com.application.mrmason.service.CustomerEmailOtpService;
import com.application.mrmason.service.CustomerLoginService;

@Service
public class CustomerEmailOtpServiceImpl implements CustomerEmailOtpService{

	@Autowired
	CustomerEmailOtpRepo emailLoginRepo;
	@Autowired
	CustomerLoginService loginService;
	
	@Override
	public String  isEmailExists(String email) {
		if(emailLoginRepo.findByEmail(email)==null) {
			return null;
		}
		return email;
	}

	@Override
	public CustomerEmailOtp updateData(String otp,String email) {
		Optional<CustomerEmailOtp> existedById = Optional.of(emailLoginRepo.findByEmail(email));
		if(existedById.isPresent()) {
			existedById.get().setOtp(otp);
			loginService.updateDataWithEmail(email);
			return emailLoginRepo.save(existedById.get());
		}
		return null;
	}
	
}
