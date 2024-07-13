package com.application.mrmason.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.mrmason.dto.ChangeForfotdto;
import com.application.mrmason.dto.FilterCustomerAndUser;
import com.application.mrmason.dto.Logindto;
import com.application.mrmason.dto.ResponseListUserDto;
import com.application.mrmason.dto.ResponseMessageDto;
import com.application.mrmason.dto.ResponseSpLoginDto;
import com.application.mrmason.dto.ResponseUserDTO;
import com.application.mrmason.dto.ResponseUserUpdateDto;
import com.application.mrmason.dto.Userdto;
import com.application.mrmason.entity.User;
import com.application.mrmason.repository.SPAvailabilityRepo;
import com.application.mrmason.repository.UserDAO;
import com.application.mrmason.service.impl.ServicePersonLoginService;
import com.application.mrmason.service.impl.UserService;

@RestController

public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserDAO userDAO;

	@Autowired
	ServicePersonLoginService loginService;

	@Autowired
	SPAvailabilityRepo availabilityReo;
	
	ResponseUserDTO response =  new ResponseUserDTO();
	ResponseUserUpdateDto userResponse = new ResponseUserUpdateDto();

	ResponseMessageDto response2=new ResponseMessageDto();

	@PostMapping("/sp-register")
	public ResponseEntity<?> create(@RequestBody User registrationDetails) {
		try {
			if (userService.isEmailExists(registrationDetails.getEmail())) {
				response.setMessage("Email already exists");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			if (userService.isMobileExists(registrationDetails.getMobile())) {
				response.setMessage("Mobile already exists");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			Userdto userDetails=userService.addDetails(registrationDetails);
			response.setMessage("User added successfully");
			response.setStatus(true);
			response.setUserData(userDetails);
			return new ResponseEntity<>( response,HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
		}
	}

	@PreAuthorize("hasAuthority('Developer')")
	@PutMapping("/sp-update-profile")
	public ResponseEntity<?> updateServiceProfile(@RequestBody User registrationDetails) {

		String email = registrationDetails.getEmail();
		User updatedUser = userService.updateProfile(registrationDetails, email);

		try {
			if (updatedUser == null) {
				response.setMessage("invalid Email");
				response.setStatus(false);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				userResponse.setMessage("Profile updated successfully");
				userResponse.setStatus(true);
				userResponse.setUserData(updatedUser);
				return ResponseEntity.ok().body(userResponse);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
		}
	}

	@PreAuthorize("hasAuthority('Developer')")
	@PostMapping("/change-password")
	
	public ResponseEntity<ResponseMessageDto> changeCustomerPassword(@RequestBody ChangeForfotdto cfPwd) {


		String email = cfPwd.getEmail();
		String oldPassword = cfPwd.getOldPassword();
		String newPassword = cfPwd.getNewPassword();
		String confirmPassword = cfPwd.getConfirmPassword();

		try {
			if (userService.changePassword(email, oldPassword, newPassword, confirmPassword) == "changed") {
				response2.setMessage("Password Changed Successfully..");
				response2.setStatus(true);
				return new ResponseEntity<>(response2, HttpStatus.OK);
				
			} else if (userService.changePassword(email, oldPassword, newPassword, confirmPassword) == "notMatched") {
				response2.setMessage("New Passwords Not Matched.!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			} else if (userService.changePassword(email, oldPassword, newPassword, confirmPassword) == "incorrect") {
				response2.setMessage("Old Password is Incorrect");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			}
		} catch (Exception e) {
			response2.setMessage(e.getMessage());
			response2.setStatus(false);
			return ResponseEntity.status(HttpStatus.OK).body(response2);
		}
		response2.setMessage("Invalid User.!");
		response2.setStatus(false);
		return new ResponseEntity<>(response2, HttpStatus.OK);
	}

	@PostMapping("/forget-pwd-send-otp")
	public ResponseEntity<ResponseMessageDto> sendOtpForPasswordChange(@RequestBody Logindto login) {
		String userMail=login.getEmail();
		String mobile=login.getMobile();
		try {
			if(userMail!=null) {
				if (userService.sendMail(userMail) != null) {
					response2.setMessage("OTP Sent to Registered EmailId.");
					response2.setStatus(true);
					return new ResponseEntity<>(response2, HttpStatus.OK);
				}
				response2.setMessage("Invalid Email ID.!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
				
			}else {
				if(userService.sendSms(mobile)!=null) {
					response2.setMessage("OTP Sent to Registered Mobile Number.");
					response2.setStatus(true);
					return new ResponseEntity<>(response2, HttpStatus.OK);
				}
				response2.setMessage("Invalid Mobile number..!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			response2.setMessage(e.getMessage());
			response2.setStatus(false);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response2);
		}
		
	}

	@PostMapping("/forget-pwd-change")
	public ResponseEntity<ResponseMessageDto> verifyOtpForPasswordChange(@RequestBody ChangeForfotdto cfPwd) {

		String email = cfPwd.getEmail();
		String otp = cfPwd.getOtp();
		String newPassword = cfPwd.getNewPassword();
		String mobile=cfPwd.getMobile();
		String confirmPassword = cfPwd.getConfirmPassword();

		try {
			String data=userService.forgetPassword(mobile, email, otp, newPassword, confirmPassword);
			if (data== "changed") {
				response2.setMessage("Password Changed Successfully..");
				response2.setStatus(true);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			} else if (data== "notMatched") {
				response2.setMessage("New Passwords Not Matched.!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			} else if (data == "incorrect") {
				response2.setMessage("Invalid OTP..!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			} else if (data == "incorrectEmail") {
				response2.setMessage("Invalid Email ID.!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			} 
		} catch (Exception e) {
			response2.setMessage(e.getMessage());
			response2.setStatus(false);
			return ResponseEntity.status(HttpStatus.OK).body(response2);
		}
		response2.setMessage("Invalid Mobile Number..!");
		response2.setStatus(false);
		return new ResponseEntity<>(response2, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('Developer')")
	@GetMapping("/sp-get-profile")
	public ResponseEntity<?> getProfile(@RequestParam(required = false)String email) {
		
		Userdto profile=userService.getServiceProfile(email);
		try {
			if ( profile == null) {
				response.setMessage("Invalid Email ....!");
				response.setStatus(false);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setMessage("Profile fetched successfully.");
			response.setStatus(true);
			response.setUserData(profile);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	
	@PostMapping("/sp-login")
	public ResponseEntity<?> login(@RequestBody Logindto login) {
		String email = login.getEmail();
		String mobile = login.getMobile();
		String password = login.getPassword();

		try {
			ResponseSpLoginDto response = userService.loginDetails(email, mobile, password);
			if (response.getJwtToken() != null) {
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
		}
	}
	@PreAuthorize("hasAuthority('Adm')")
	@GetMapping("/filterServicePerson")
	public ResponseEntity<?> getCustomers(@RequestBody FilterCustomerAndUser user) {
		ResponseListUserDto response3=new ResponseListUserDto();
		
		String userEmail = user.getUserEmail();
		String userMobile = user.getUserMobile();
		String userState = user.getUserState();
		String fromDate = user.getFromDate();
		String toDate = user.getToDate();
		String status = user.getStatus();
		String category = user.getServiceCategory();
		try {
			List<User> entity = userService.getServicePersonData(userEmail, userMobile, userState, status, category,
					fromDate, toDate);
			if (!entity.isEmpty()) {
				response3.setMessage("Service person details fetched successfully.!");
				response3.setStatus(true);
				response3.setData(entity);
				return ResponseEntity.ok(response3);
			} else {
				response3.setMessage("No data found for the given details.!");
				response3.setStatus(true);
				return ResponseEntity.status(HttpStatus.OK)
						.body(response3);
			}
		} catch (Exception e) {
			response3.setMessage(e.getMessage());
			response3.setStatus(false);
			return ResponseEntity.status(HttpStatus.OK).body(response3);
		}

	}
	@GetMapping("/getData")
	public ResponseEntity<User> get(@RequestParam(name = "email") String email) {

		return new ResponseEntity<>(userService.getServiceDataProfile(email), HttpStatus.OK);

	}

	@GetMapping("/error")
	@PostMapping("/error")
	@PutMapping("/error")
	@DeleteMapping("/error")
	public ResponseEntity<ResponseMessageDto> error() {
		response2.setMessage("Access Denied");
		response2.setStatus(false);
		return new ResponseEntity<ResponseMessageDto>(response2,HttpStatus.UNAUTHORIZED);

	}

}
