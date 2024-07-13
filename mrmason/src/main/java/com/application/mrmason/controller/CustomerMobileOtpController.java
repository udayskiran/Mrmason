package com.application.mrmason.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.application.mrmason.dto.Logindto;
import com.application.mrmason.dto.ResponseMessageDto;
import com.application.mrmason.entity.CustomerMobileOtp;
import com.application.mrmason.repository.CustomerMobileOtpRepo;
import com.application.mrmason.service.CustomerMobileOtpService;
import com.application.mrmason.service.CustomerRegistrationService;
import com.application.mrmason.service.OtpGenerationService;

@RestController
public class CustomerMobileOtpController {
	@Autowired
	OtpGenerationService otpService;
	@Autowired
	CustomerMobileOtpService mobileService;
	@Autowired
	CustomerRegistrationService regService;
	@Autowired
	CustomerMobileOtpRepo otpRepo;
	ResponseMessageDto response=new ResponseMessageDto();
	@PostMapping("/sendSmsOtp")
	public ResponseEntity<ResponseMessageDto> sendMobileOtp(@RequestBody Logindto login) {
		String mobile = login.getMobile();
		if (otpRepo.findByMobileNum(mobile) == null) {
			response.setMessage("Invalid mobile number..!");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			Optional<CustomerMobileOtp> user = Optional.of(otpRepo.findByMobileNum(mobile));

			if (user.get().getOtp() == null) {
				otpService.generateMobileOtp(mobile);
				response.setStatus(true);
				response.setMessage("Otp sent to the registered mobile number.");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setMessage("Mobile number already verified.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PostMapping("/verifySmsOtp")
	public ResponseEntity<ResponseMessageDto> verifyMobileOtp(@RequestBody Logindto login) {
		String mobile = login.getMobile();
		String otp = login.getOtp();

		if (otpService.verifyMobileOtp(mobile, otp)) {

			mobileService.updateData(otp, mobile);
			response.setStatus(true);
			response.setMessage(" Email Verified successful");
			return new ResponseEntity<>(response, HttpStatus.OK);

		}
		response.setMessage("Incorrect OTP, Please enter correct Otp");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
