package com.application.mrmason.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.application.mrmason.dto.AdminDetailsDto;
import com.application.mrmason.dto.ResponceAdminDetailsDto;
import com.application.mrmason.entity.AdminDetails;
import com.application.mrmason.repository.AdminDetailsRepo;
import com.application.mrmason.security.JwtService;
import com.application.mrmason.service.AdminDetailsService;
import com.application.mrmason.service.OtpGenerationService;


@Service
public class AdminDetailsServiceImpl implements AdminDetailsService {
	@Autowired
	public AdminDetailsRepo adminRepo;
	
	@Autowired
	ModelMapper model;
	@Autowired
	JwtService jwtService;
	
	@Autowired
	OtpGenerationService otpService;
	
	BCryptPasswordEncoder byCrypt = new BCryptPasswordEncoder();
	@Override
	public AdminDetails registerDetails(AdminDetails admin) {
		BCryptPasswordEncoder byCrypt = new BCryptPasswordEncoder();

		Optional<AdminDetails> user = Optional
				.ofNullable(adminRepo.findByEmailOrMobile(admin.getEmail(), admin.getMobile()));
		if (!user.isPresent()) {
			String encryptPassword = byCrypt.encode(admin.getPassword());
			admin.setPassword(encryptPassword);
			return adminRepo.save(admin);
		}
		return null;
	}

	@Override
	public AdminDetailsDto getDetails(String email, String phno) {
		Optional<AdminDetails> user = Optional
				.ofNullable(adminRepo.findByEmailOrMobile(email, phno));
		AdminDetails adminDetails = user.get();
		if (user.isPresent()) {
			AdminDetailsDto adminDto = new AdminDetailsDto();
			adminDto.setId(adminDetails.getId());
			adminDto.setAdminName(adminDetails.getAdminName());
			adminDto.setAdminType(String.valueOf(adminDetails.getUserType()));
			adminDto.setEmail(adminDetails.getEmail());
			adminDto.setMobile(adminDetails.getMobile());
			adminDto.setStatus(adminDetails.getStatus());
			adminDto.setRegDate(adminDetails.getRegDate());

			return adminDto;
		}
		return null;
	}

	@Override
	public AdminDetailsDto getAdminDetails(String email,String mobile) {

		Optional<AdminDetails> user = Optional
				.ofNullable(adminRepo.findByEmailOrMobile(email, mobile));
		AdminDetails adminDetails = user.get();
		if (user.isPresent()) {
			AdminDetailsDto adminDto = new AdminDetailsDto();
//			admin.setId(adminDetails.getId());
//			admin.setAdminName(adminDetails.getAdminName());
//			admin.setAdminType(String.valueOf(adminDetails.getUserType()));
//			admin.setEmail(adminDetails.getEmail());
//			admin.setMobile(adminDetails.getMobile());
//			admin.setStatus(adminDetails.getStatus());
//			admin.setRegDate(adminDetails.getRegDate());
			AdminDetailsDto admin=model.map(adminDetails, adminDto.getClass());
			return admin;
		}

		return null;
	}

	@Override
	public String updateAdminData(AdminDetails admin) {
		Optional<AdminDetails> user = Optional
				.ofNullable(adminRepo.findByEmailOrMobile(admin.getEmail(), admin.getMobile()));
		if (user.isPresent()) {
			user.get().setAdminName(admin.getAdminName());
			user.get().setUserType(admin.getUserType());

			adminRepo.save(user.get());
			return "Success";
		} else {
			return null;
		}
	}
	
	

	@Override
	public ResponceAdminDetailsDto adminLoginDetails(String userEmail, String phno, String userPassword) {
		BCryptPasswordEncoder byCrypt = new BCryptPasswordEncoder();
		ResponceAdminDetailsDto response=new ResponceAdminDetailsDto();
		if (adminRepo.findByEmailOrMobile(userEmail, phno) != null) {
			Optional<AdminDetails> user = Optional.ofNullable(adminRepo.findByEmailOrMobile(userEmail, phno));
			
			if (user.isPresent()) {
				AdminDetails loginDb = user.get();
				if (loginDb.getStatus().equalsIgnoreCase("active")) {
					if (userEmail != null || phno != null) {

						if (byCrypt.matches(userPassword, loginDb.getPassword())) {
							String jwtToken = jwtService.generateToken(loginDb);
							response.setJwtToken(jwtToken);
							response.setStatus(true);
							response.setMessage("Login Successful.");
							response.setData(getDetails(userEmail, phno));
							return response;
						} else {
							response.setMessage("Invalid Password.");
							return response;
						}

					}
				} else {
					response.setMessage("Inactive User");
					return response;
				}
			}
		}
		response.setMessage("Invalid User.!");
		return response;
	}
	
	@Override
	public String changePassword(String usermail, String oldPass, String newPass, String confPass, String phno) {
		BCryptPasswordEncoder byCrypt=new BCryptPasswordEncoder();
		Optional<AdminDetails> user= Optional.of(adminRepo.findByEmailOrMobile(usermail, phno));
		if(user.isPresent()) {
			if(byCrypt.matches(oldPass,user.get().getPassword() )) {
				if(newPass.equals(confPass)) {
					String encryptPassword =byCrypt.encode(confPass);
					user.get().setPassword(encryptPassword);
					adminRepo.save(user.get());
					return "changed";
				}else {
					return "notMatched";
				}
			}else {
				return "incorrect";
			}
		}else {
			return "invalid";
		}
	}
	
	@Override
	public String sendMail(String email) {
		Optional<AdminDetails> userOp = Optional.ofNullable(adminRepo.findByEmail(email));
		if (userOp.isPresent()) {
			otpService.generateOtp(email);
			return "otp";
		}
		return null;
	}
	@Override
	public String sendSms(String mobile) {
		Optional<AdminDetails> userOp = Optional.ofNullable(adminRepo.findByMobile(mobile));
		if (userOp.isPresent()) {
			otpService.generateMobileOtp(mobile);
			return "otp";
		}
		return null;
	}
	@Override
	public String forgetPassword(String mobile,String email, String otp, String newPass, String confPass) {
		Optional<AdminDetails> userEmail = Optional.ofNullable(adminRepo.findByEmail(email));
		Optional<AdminDetails> userMobile = Optional.ofNullable(adminRepo.findByMobile(mobile));
		if (userEmail.isPresent()) {
			if (otpService.verifyOtp(email, otp)) {
				if (newPass.equals(confPass)) {
					String encryptPassword = byCrypt.encode(confPass);
					userEmail.get().setPassword(encryptPassword);
					adminRepo.save(userEmail.get());
					return "changed";
				} else {
					return "notMatched";
				}
			} else {
				return "incorrect";
			}
		}else if(userMobile.isPresent()) {
			if (otpService.verifyMobileOtp(mobile, otp)) {
				if (newPass.equals(confPass)) {
					String encryptPassword = byCrypt.encode(confPass);
					userMobile.get().setPassword(encryptPassword);
					adminRepo.save(userMobile.get());
					return "changed";
				} else {
					return "notMatched";
				}
			} else {
				return "incorrect";
			}
		}
		else if(!userEmail.isPresent()&& userMobile.isPresent()) {
			return "incorrectEmail";
		}
		return null;

	}
}
