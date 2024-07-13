package com.application.mrmason.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.mrmason.dto.ChangePasswordDto;
import com.application.mrmason.dto.FilterCustomerAndUser;
import com.application.mrmason.dto.Logindto;
import com.application.mrmason.dto.ResponseCustomerRegDto;
import com.application.mrmason.dto.ResponseListCustomerData;
import com.application.mrmason.dto.ResponseLoginDto;
import com.application.mrmason.dto.ResponseMessageDto;
import com.application.mrmason.dto.ResponseUpdateDto;
import com.application.mrmason.dto.UpdateProfileDto;
import com.application.mrmason.entity.CustomerRegistration;
import com.application.mrmason.service.CustomerRegistrationService;

@RestController
public class CustomerRegistrationController {

	@Autowired
	public CustomerRegistrationService service;
	ResponseMessageDto response2 = new ResponseMessageDto();
	ResponseLoginDto response3=new ResponseLoginDto();
	ResponseCustomerRegDto response = new ResponseCustomerRegDto();
	@PostMapping("/addNewUser")
	public ResponseEntity<?> newCustomer(@RequestBody CustomerRegistration customer) {
		if (!service.isUserUnique(customer)) {
			return new ResponseEntity<>("Email or Phone Number already exists.", HttpStatus.OK);
		}
		
		response.setData(service.saveData(customer));
		response.setMessage("Customer added Successfully..");
		response.setStatus(true);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAuthority('Adm')")
	@GetMapping("/filterCustomers")
	public ResponseEntity<ResponseListCustomerData> getCustomers(@RequestBody FilterCustomerAndUser customer) {
		ResponseListCustomerData response=new ResponseListCustomerData();
		
		String userEmail = customer.getUserEmail();
		String userMobile = customer.getUserMobile();
		String userState = customer.getUserState();
		String fromDate = customer.getFromDate();
		String toDate = customer.getToDate();
		try {
			List<CustomerRegistration> entity = service.getCustomerData(userEmail, userMobile, userState, fromDate,
					toDate);
			if (!entity.isEmpty()) {
				response.setMessage("Fetched users successfully.");
				response.setStatus(true);
				response.setData(entity);
				return ResponseEntity.ok(response);
			} else {
				response.setMessage("No data found for the given details.!");
				response.setStatus(true);
				return ResponseEntity.status(HttpStatus.OK)
						.body(response);
			}
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	@GetMapping("/getProfile")
	public ResponseEntity<?> getProfile(Authentication authentication) {
		try {
			CustomerRegistration userPrincipal = (CustomerRegistration) authentication.getPrincipal();
			response.setMessage("Profile fetched succeslly.!");
			response.setStatus(true);
			response.setData(service.getProfileData(userPrincipal.getUserid()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PreAuthorize("hasAuthority('EC')")
	@PutMapping("/updateProfile")

	public ResponseEntity<?> updateCustomer(@RequestBody UpdateProfileDto request) {
		String userName = request.getUserName();
		String userTown = request.getUserTown();
		String userState = request.getUserState();
		String userDistrict = request.getUserDistrict();
		String userPinCode = request.getUserPincode();
		String userid = request.getUserid();

		ResponseUpdateDto response = new ResponseUpdateDto();
		try {
			if (service.updateCustomerData(userName, userTown, userState, userDistrict, userPinCode, userid) != null) {
				response.setUpdateProfile(service.getProfileData(userid));
				response.setMessage("Successfully Updated.");
				response.setStatus(true);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setMessage("Profile Not Found.");
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
	}

	@PreAuthorize("hasAuthority('EC')")
	@PostMapping("/changePassword")
	public ResponseEntity<?> changeCustomerPassword(@RequestBody ChangePasswordDto request) {
		String userMail = request.getUserMail();
		String oldPass = request.getOldPass();
		String newPass = request.getNewPass();
		String confPass = request.getConfPass();
		String userMobile = request.getUserMobile();

		try {
			if (service.changePassword(userMail, oldPass, newPass, confPass, userMobile) == "changed") {
				response2.setMessage("Password Changed Successfully..");
				response2.setStatus(true);
				return new ResponseEntity<>(response2, HttpStatus.OK);

			} else if (service.changePassword(userMail, oldPass, newPass, confPass, userMobile) == "notMatched") {
				response2.setMessage("New Passwords Not Matched.!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			} else if (service.changePassword(userMail, oldPass, newPass, confPass, userMobile) == "incorrect") {
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

	@PostMapping("/login")
	public ResponseEntity<ResponseLoginDto> login(@RequestBody Logindto requestDto) {
		String userEmail = requestDto.getEmail();
		String phno = requestDto.getMobile();
		String userPassword = requestDto.getPassword();
		
		try {
			ResponseLoginDto response = service.loginDetails(userEmail, phno, userPassword);
			if (response.getJwtToken() != null) {
				return new ResponseEntity<ResponseLoginDto>(response, HttpStatus.OK);
			}
			return new ResponseEntity<ResponseLoginDto>(response, HttpStatus.OK);
		}catch(Exception e) {
			response3.setMessage(e.getMessage());
			return new ResponseEntity<ResponseLoginDto>(response3, HttpStatus.OK);
		}
		

	}
}
