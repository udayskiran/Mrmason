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
import com.application.mrmason.entity.ServicePersonLogin;
import com.application.mrmason.repository.ServicePersonLoginDAO;
import com.application.mrmason.service.impl.OtpGenerationServiceImpl;
import com.application.mrmason.service.impl.ServicePersonLoginService;
import com.application.mrmason.service.impl.UserService;

@RestController

public class ServicePersonLoginController {

	@Autowired
	ServicePersonLoginService loginService;

	@Autowired
	OtpGenerationServiceImpl otpService;
	

	@Autowired
	UserService userService;

	@Autowired
	ServicePersonLoginDAO servicePersonDao;
	
	ResponseMessageDto response=new ResponseMessageDto();

	@PostMapping("/sp-send-email-otp")
	public ResponseEntity<ResponseMessageDto> sendEmail(@RequestBody ServicePersonLogin login) {
		String email = login.getEmail();

		try {
			if (loginService.isEmailExists(email) == null) {
				response.setMessage("Invalid EmailId..!");
				response.setStatus(false);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				Optional<ServicePersonLogin> user = Optional.of(servicePersonDao.findByEmail(email));

				if (user.get().getEVerify().equalsIgnoreCase("no")) {
					otpService.generateOtp(email);
					response.setStatus(true);
					response.setMessage("Otp sent to the registered EmailId.");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				response.setStatus(false);
				response.setMessage("Email already verified.");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	@PostMapping("/sp-verify-email-otp")
	public ResponseEntity<ResponseMessageDto> verifyUserEmail(@RequestBody ServicePersonLogin login) {
		String email = login.getEmail();
		String eOtp = login.getEOtp();

		try {
			if (otpService.verifyOtp(email, eOtp)) {

				loginService.updateEmailData(eOtp, email);
				response.setStatus(true);
				response.setMessage("Email Verified successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);

			}
			response.setStatus(false);
			response.setMessage("Incorrect OTP, Please enter correct Otp");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	
	@PostMapping("/sp-send-mobile-otp")
	public ResponseEntity<ResponseMessageDto> sendSms(@RequestBody Logindto login) {
		String mobile = login.getMobile();
		if (servicePersonDao.findByMobile(mobile) == null) {
			response.setMessage("Invalid mobile number..!");
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			Optional<ServicePersonLogin> user = Optional.of(servicePersonDao.findByMobile(mobile));

			if (user.get().getMobVerify().equalsIgnoreCase("no")) {
				otpService.generateMobileOtp(mobile);
				response.setStatus(true);
				response.setMessage("Otp sent to the registered mobile number.");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setMessage("Mobile number already verified.");
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PostMapping("/sp-verify-mobile-otp")
	public ResponseEntity<ResponseMessageDto> verifyMobile(@RequestBody Logindto login) {
		String mobile = login.getMobile();
		String otp = login.getOtp();

		if (otpService.verifyMobileOtp(mobile, otp)) {

			loginService.updateMobileData(otp, mobile);
			response.setStatus(true);
			response.setMessage("Mobile number Verified successful");
			return new ResponseEntity<>(response, HttpStatus.OK);

		}
		response.setStatus(false);
		response.setMessage("Incorrect OTP, Please enter correct Otp");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	
}