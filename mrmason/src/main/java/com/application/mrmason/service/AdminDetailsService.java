package com.application.mrmason.service;

import com.application.mrmason.dto.AdminDetailsDto;
import com.application.mrmason.dto.ResponceAdminDetailsDto;
import com.application.mrmason.entity.AdminDetails;

public interface AdminDetailsService {
	AdminDetails registerDetails(AdminDetails admin);

	AdminDetailsDto getDetails(String email, String phno);

	AdminDetailsDto getAdminDetails(String email,String mobile);

	String updateAdminData(AdminDetails admin);

	ResponceAdminDetailsDto adminLoginDetails(String userEmail, String phno, String userPassword);

	String changePassword(String usermail, String oldPass, String newPass, String confPass, String phno);

	String forgetPassword(String mobile,String email, String otp, String newPass, String confPass);

	String sendMail(String email);
	String sendSms(String mobile);
}
