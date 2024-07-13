package com.application.mrmason.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.mrmason.dto.CustomerRegistrationDto;
import com.application.mrmason.dto.ResponseLoginDto;
import com.application.mrmason.entity.CustomerEmailOtp;
import com.application.mrmason.entity.CustomerLogin;
import com.application.mrmason.entity.CustomerMobileOtp;
import com.application.mrmason.entity.CustomerRegistration;
import com.application.mrmason.entity.UserType;
import com.application.mrmason.repository.CustomerEmailOtpRepo;
import com.application.mrmason.repository.CustomerLoginRepo;
import com.application.mrmason.repository.CustomerMobileOtpRepo;
import com.application.mrmason.repository.CustomerRegistrationRepo;
import com.application.mrmason.security.JwtService;
import com.application.mrmason.service.CustomerRegistrationService;


@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {

	@Autowired
	CustomerEmailOtpRepo emailRepo;
	@Autowired
	CustomerMobileOtpRepo mobileRepo;
	@Autowired
	CustomerRegistrationRepo repo;
	@Autowired
    CustomerLoginRepo loginRepo;
	@Autowired
	BCryptPasswordEncoder byCrypt;
	@Autowired
	JwtService jwtService;
	@Autowired
	CustomerRegistrationRepo customerRegistrationRepo;

	@Override
	public CustomerRegistrationDto saveData(CustomerRegistration customer) {
		BCryptPasswordEncoder byCrypt = new BCryptPasswordEncoder();
		String encryptPassword = byCrypt.encode(customer.getUserPassword());
		customer.setUserPassword(encryptPassword);
		CustomerRegistration registration= repo.save(customer);

		CustomerLogin loginEntity = new CustomerLogin();
		loginEntity.setUserEmail(customer.getUserEmail());
		loginEntity.setUserMobile(customer.getUserMobile());
		loginEntity.setUserPassword(customer.getUserPassword());
		loginEntity.setMobileVerified("no");
		loginEntity.setEmailVerified("no");
		loginEntity.setStatus("inactive");

		loginRepo.save(loginEntity);

		CustomerEmailOtp emailLoginEntity = new CustomerEmailOtp();
		emailLoginEntity.setEmail(customer.getUserEmail());

		emailRepo.save(emailLoginEntity);
		
		CustomerMobileOtp mobileLoginEntity = new CustomerMobileOtp();
		mobileLoginEntity.setMobileNum(customer.getUserMobile());
		
		mobileRepo.save(mobileLoginEntity);
		

		CustomerRegistrationDto customerDto = new CustomerRegistrationDto();
		customerDto.setId(customer.getId());
		customerDto.setUserName(customer.getUsername());
		customerDto.setUserEmail(customer.getUserEmail());
		customerDto.setUserid(customer.getUserid());
		customerDto.setUserMobile(customer.getUserMobile());
		customerDto.setRegDate(customer.getRegDate());
		customerDto.setUserPincode(customer.getUserPincode());
		customerDto.setUserState(customer.getUserState());
		customerDto.setUserTown(customer.getUserTown());
		customerDto.setUsertype(String.valueOf(registration.getUserType()));
		customerDto.setUserDistrict(customer.getUserDistrict());

		return customerDto;
	}

	@Override
	public boolean isUserUnique(CustomerRegistration customer) {
		CustomerRegistration user = repo.findByUserEmailOrUserMobile(customer.getUserEmail(), customer.getUserMobile());
		return user == null;
	}

	@Override
	public List<CustomerRegistration> getCustomerData(String email, String phNo, String userState, String fromDate,
			String toDate) {
		if (fromDate == null && toDate == null && email != null || phNo != null || userState != null) {
			return repo.findAllByUserEmailOrUserMobileOrUserState(email, phNo, userState);
		} else {
			return repo.findByRegDateBetween(fromDate, toDate);
		}

	}

	@Override
	public String updateCustomerData(String userName, String userTown, String userState, String userDist,
			String userPinCode, String userid) {
		Optional<CustomerRegistration> existedById = Optional.of(repo.findByUserid(userid));
		if (existedById.isPresent()) {
			existedById.get().setUserName(userName);
			existedById.get().setUserPincode(userPinCode);
			existedById.get().setUserState(userState);
			existedById.get().setUserTown(userTown);
			existedById.get().setUserDistrict(userDist);
			repo.save(existedById.get());
			return "Success";
		} else {
			return null;
		}
	}

	@Override
	public CustomerRegistration getCustomer(String email, String phno) {
		return repo.findByUserEmailOrUserMobile(email, phno);
	}

	public CustomerRegistrationDto getProfileData(String userid) {
		CustomerRegistrationDto customerDto = new CustomerRegistrationDto();
		Optional<CustomerRegistration> user = Optional.of(repo.findByUserid(userid));
		customerDto.setId(user.get().getId());
		customerDto.setRegDate(user.get().getRegDate());
		customerDto.setUserDistrict((user.get().getUserDistrict()));
		customerDto.setUserEmail(user.get().getUserEmail());
		customerDto.setUserid(user.get().getUserid());
		customerDto.setUserMobile(user.get().getUserMobile());
		customerDto.setUserName(user.get().getUsername());
		customerDto.setUserPincode(user.get().getUserPincode());
		customerDto.setUserState(user.get().getUserState());
		customerDto.setUserTown(user.get().getUserTown());
		customerDto.setUsertype(String.valueOf(user.get().getUserType()));
		return customerDto;
	}

	@Override
	public String changePassword(String usermail, String oldPass, String newPass, String confPass, String phno) {
		BCryptPasswordEncoder byCrypt = new BCryptPasswordEncoder();
		Optional<CustomerLogin> user = Optional.of(loginRepo.findByUserEmailOrUserMobile(usermail, phno));
		if (user.isPresent()) {
			if (byCrypt.matches(oldPass, user.get().getUserPassword())) {
				if (newPass.equals(confPass)) {
					String encryptPassword = byCrypt.encode(confPass);
					user.get().setUserPassword(encryptPassword);
					loginRepo.save(user.get());
					return "changed";
				} else {
					return "notMatched";
				}
			} else {
				return "incorrect";
			}
		} else {
			return "invalid";
		}

	}

	@Override
	public ResponseLoginDto loginDetails(String userEmail, String phno, String userPassword) {
		ResponseLoginDto response = new ResponseLoginDto();

		CustomerLogin loginDb = loginRepo.findByUserEmailOrUserMobile(userEmail, phno);
		if (loginDb != null) {
			if (loginDb.getStatus().equalsIgnoreCase("active")) {
				CustomerRegistration customerRegistration = customerRegistrationRepo
						.findByUserEmail(loginDb.getUserEmail());
				if (userEmail != null && phno == null) {
					if (loginDb.getEmailVerified().equalsIgnoreCase("yes")) {
						if (byCrypt.matches(userPassword, loginDb.getUserPassword())) {
							String jwtToken = jwtService.generateToken(customerRegistration);
							response.setMessage("Login Successful.");
							response.setJwtToken(jwtToken);
							response.setStatus(true);
							response.setLoginDetails(getProfileData(customerRegistration.getUserid()));
							return response;

						} else {
							response.setMessage("Invalid Password");
							return response;
						}
					} else {
						response.setMessage("verify Email");
						return response;
					}
				} else if (userEmail == null && phno != null) {
					if (loginDb.getMobileVerified().equalsIgnoreCase("yes")) {
						CustomerRegistration user=repo.findByUserEmailOrUserMobile(userEmail, phno);
						if (byCrypt.matches(userPassword, loginDb.getUserPassword())) {
							String jwtToken = jwtService.generateToken(customerRegistration);
							response.setJwtToken(jwtToken);
							response.setMessage("Login Successful.");
							response.setStatus(true);
							response.setLoginDetails(getProfileData(user.getUserid()));
							return response;
						} else {
							response.setMessage("Invalid Password");
							return response;
						}
					} else {
						response.setMessage("verify Mobile");
						return response;
					}
				}
			} else {
				response.setMessage("Inactive User");
				return response;
			}
		}

		response.setMessage("Invalid User.!");
		return response;
	}

}
