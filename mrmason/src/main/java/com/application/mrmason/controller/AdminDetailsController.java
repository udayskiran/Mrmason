package com.application.mrmason.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.mrmason.dto.AdminDetailsDto;
import com.application.mrmason.dto.ChangePasswordDto;
import com.application.mrmason.dto.Logindto;
import com.application.mrmason.dto.ResponceAdminDetailsDto;
import com.application.mrmason.dto.ResponseListAdminDetailsDto;
import com.application.mrmason.dto.ResponseMessageDto;
import com.application.mrmason.entity.AdminDetails;
import com.application.mrmason.service.AdminDetailsService;

@RestController
public class AdminDetailsController {
	@Autowired
	public AdminDetailsService adminService;
	ResponseListAdminDetailsDto response=new ResponseListAdminDetailsDto();
	
	ResponseMessageDto response2 = new ResponseMessageDto();
	@PostMapping("/addAdminDetails")
	public ResponseEntity<?> saveAdminDetails(@RequestBody AdminDetails admin) {
		ResponceAdminDetailsDto response = new ResponceAdminDetailsDto();
		String userEmail = admin.getEmail();
		String userMobile = admin.getMobile();
		try {
			if (adminService.registerDetails(admin) != null) {

				response.setData(adminService.getDetails(userEmail, userMobile));
				response.setMessage("Admin details added successfully..");
				response.setStatus(true);
				return ResponseEntity.ok(response);
			}
			response.setMessage("Email or Mobile already exists.!");
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}

	}
	@PreAuthorize("hasAuthority('Adm')")
	@PutMapping("/updateAdminDetails")
	public ResponseEntity<?> updateCustomer(@RequestBody AdminDetails admin) {
		ResponceAdminDetailsDto response = new ResponceAdminDetailsDto();
		String userEmail = admin.getEmail();
		String userMobile = admin.getMobile();
		try {
			if (adminService.updateAdminData(admin) != null) {
				response.setData(adminService.getDetails(userEmail, userMobile));
				response.setMessage("Successfully Updated.");
				response.setStatus(true);
				return ResponseEntity.ok(response);

			}
			response.setMessage("Admin Not Found.");
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}

	}
	@PreAuthorize("hasAuthority('Adm')")
	@GetMapping("/getAdminDetails")
	public ResponseEntity<?> getAdminDetails(@RequestParam(required = false)String email,@RequestParam(required = false) String mobile) {
		try {
			AdminDetailsDto entity = adminService.getAdminDetails(email, mobile);
			if (entity == null) {
				response.setMessage("Invalid User.!");
				response.setStatus(false);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setData(entity);
			response.setMessage("Admin data fetched successfully..");
			response.setStatus(true);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}
	
	@PostMapping("/adminLoginWithPass")
	public ResponseEntity<?> login(@RequestBody Logindto login) {
		String userEmail = login.getEmail();
		String userMobile = login.getMobile();
		String userPassword = login.getPassword();
		ResponceAdminDetailsDto response=adminService.adminLoginDetails(userEmail, userMobile, userPassword);
		
		try {
			if (response.getJwtToken()!=null) {
				
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setMessage("Invalid Admin.!");
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	
	}
	@PreAuthorize("hasAuthority('Adm')")
	@PostMapping("/changeAdminPassword")
	public ResponseEntity<ResponseMessageDto> changeCustomerPassword(@RequestBody ChangePasswordDto request) {
		String userMail = request.getUserMail();
		String oldPass = request.getOldPass();
		String newPass = request.getNewPass();
		String confPass = request.getConfPass();
		String userMobile=request.getUserMobile();
		
		try {
			if (adminService.changePassword(userMail, oldPass, newPass, confPass,userMobile) == "changed") {
				response2.setMessage("Password Changed Successfully..");
				response2.setStatus(true);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			} else if (adminService.changePassword(userMail, oldPass, newPass, confPass,userMobile) == "notMatched") {
				response2.setMessage("New Passwords Not Matched.!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			} else if (adminService.changePassword(userMail, oldPass, newPass, confPass,userMobile) == "incorrect") {
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
	
	@PostMapping("/admin/forgetPassword/sendOtp")
	public ResponseEntity<ResponseMessageDto> sendOtpForPasswordChange(@RequestBody Logindto login) {
		String userMail=login.getEmail();
		String mobile=login.getMobile();
		try {
			if(userMail!=null&& mobile==null) {
				if (adminService.sendMail(userMail) != null) {
					response2.setMessage("OTP Sent to Registered EmailId.");
					response2.setStatus(true);
					return new ResponseEntity<>(response2, HttpStatus.OK);
				}
				response2.setMessage("Invalid EmailId..!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			}else {
				if (adminService.sendSms(mobile) != null) {
					response2.setMessage("OTP Sent to Registered Mobile Number..");
					response2.setStatus(true);
					return new ResponseEntity<>(response2, HttpStatus.OK);
				}
				response2.setMessage("Invalid Mobile Number..!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			response2.setMessage("Invalid EmailId..!");
			response2.setStatus(false);
			return new ResponseEntity<>(response2, HttpStatus.OK);
		}
		
	}
	
	@PostMapping("/admin/forgetPassword/verifyOtpAndChangePassword")
	public ResponseEntity<ResponseMessageDto> verifyOtpForPasswordChange(@RequestBody ChangePasswordDto request) {
		String userMail = request.getUserMail();
		String otp = request.getOtp();
		String newPass = request.getNewPass();
		String confPass = request.getConfPass();
		String mobile = request.getUserMobile();
		try {
			String data=adminService.forgetPassword(mobile, userMail, otp, newPass, confPass);
			if (data == "changed") {
				response2.setMessage("Password Changed Successfully..");
				response2.setStatus(true);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			} else if (data == "notMatched") {
				response2.setMessage("New Passwords Not Matched.!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			} else if (data== "incorrect") {
				response2.setMessage("Invalid OTP..!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			}else if (data== "incorrectEmail") {
				response2.setMessage("Invalid Email ID.!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			}else {
				response2.setMessage("Invalid Mobile Number..!");
				response2.setStatus(false);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			}
		} catch (Exception e) {
			response2.setMessage(e.getMessage());
			response2.setStatus(false);
			return ResponseEntity.status(HttpStatus.OK).body(response2);
		}
		
	}
	
}
