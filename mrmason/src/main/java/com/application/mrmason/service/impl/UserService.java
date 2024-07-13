package com.application.mrmason.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.application.mrmason.dto.ResponseSpLoginDto;
import com.application.mrmason.dto.Userdto;
import com.application.mrmason.entity.ServicePersonLogin;
import com.application.mrmason.entity.User;
import com.application.mrmason.repository.ServicePersonLoginDAO;
import com.application.mrmason.repository.UserDAO;
import com.application.mrmason.security.JwtService;


@Service
public class UserService {
	@Autowired
	OtpGenerationServiceImpl otpService;
	
	@Autowired
	ServicePersonLoginDAO emailLoginRepo;

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	ServicePersonLoginDAO serviceLoginRepo;
	@Autowired
	BCryptPasswordEncoder byCrypt;

	public boolean isEmailExists(String email) {
		return userDAO.existsByEmail(email);
	}

	public boolean isMobileExists(String mobile) {
		return userDAO.existsByMobile(mobile);
	}

	public Userdto addDetails(User user) {
		String encryptPassword = byCrypt.encode(user.getPassword());
		user.setPassword(encryptPassword);
		User data= userDAO.save(user);

		ServicePersonLogin service = new ServicePersonLogin();
		service.setEmail(user.getEmail());
		service.setMobile(user.getMobile());
		service.setMobVerify("no");
		service.setEVerify("no");

		serviceLoginRepo.save(service);

		Userdto dto = new Userdto();
		dto.setName(user.getName());
		dto.setMobile(user.getMobile());
		dto.setEmail(user.getEmail());
		dto.setAddress(user.getAddress());
		dto.setCity(user.getCity());
		dto.setDistrict(user.getDistrict());
		dto.setState(user.getState());
		dto.setPincodeNo(user.getPincodeNo());
		dto.setVerified(user.getVerified());
		dto.setUserType(String.valueOf(data.getUserType()));
		dto.setStatus(user.getStatus());
		dto.setBusinessName(user.getBusinessName());
		dto.setBodSeqNo(user.getBodSeqNo());
		dto.setRegisteredDate(user.getRegisteredDate());
		dto.setUpdatedDate(user.getUpdatedDate());
		dto.setServiceCategory(user.getServiceCategory());

		return dto;

	}

	public User updateDataWithEmail(String email) {
		Optional<User> existedByEmail = Optional.of(userDAO.findByEmail(email));
		if (existedByEmail.isPresent()) {
			existedByEmail.get().setVerified("Yes");
			existedByEmail.get().setStatus("Active");
			return userDAO.save(existedByEmail.get());
		}
		return null;
	}
	public User updateDataWithMobile(String mobile) {
		Optional<User> existedByMobile = Optional.of(userDAO.findByEmailOrMobile(mobile, mobile));
		if (existedByMobile.isPresent()) {
			existedByMobile.get().setVerified("yes");
			existedByMobile.get().setStatus("active");
			return userDAO.save(existedByMobile.get());
		}
		return null;
	}

	public User updateProfile(User registrationDetails, String email) {
		Optional<User> existedByEmail = Optional.of(userDAO.findByEmail(email));
		if (existedByEmail.isPresent()) {

			existedByEmail.get().setName(registrationDetails.getName());
			existedByEmail.get().setPincodeNo(registrationDetails.getPincodeNo());
			existedByEmail.get().setState(registrationDetails.getState());
			existedByEmail.get().setDistrict(registrationDetails.getDistrict());
			existedByEmail.get().setAddress(registrationDetails.getAddress());
			existedByEmail.get().setCity(registrationDetails.getCity());

			return userDAO.save(existedByEmail.get());
		}
		return null;
	}

	public String changePassword(String email, String oldPassword, String newPassword, String confirmPassword) {
		Optional<User> user = Optional.of(userDAO.findByEmail(email));
		if (user.isPresent()) {
			if (byCrypt.matches(oldPassword, user.get().getPassword())) {
				if (newPassword.equals(confirmPassword)) {
					String encryptPassword = byCrypt.encode(confirmPassword);
					user.get().setPassword(encryptPassword);
					userDAO.save(user.get());
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

	public String sendMail(String email) {
		Optional<User> userOp = Optional.ofNullable(userDAO.findByEmail(email));
		if (userOp.isPresent()) {
			otpService.generateOtp(email);
			return "otp";
		}
		return null;
	}
	public String sendSms(String mobile) {
		Optional<User> userOp = Optional.ofNullable(userDAO.findByMobile(mobile));
		if (userOp.isPresent()) {
			otpService.generateMobileOtp(mobile);
			return "otp";
		}
		return null;
	}
	public String forgetPassword(String mobile,String email, String otp, String newPass, String confPass) {
		Optional<User> userEmail = Optional.ofNullable(userDAO.findByEmail(email));
		Optional<User> userMobile = Optional.ofNullable(userDAO.findByMobile(mobile));
		if (userEmail.isPresent()) {
			if (otpService.verifyOtp(email, otp)) {
				if (newPass.equals(confPass)) {
					String encryptPassword = byCrypt.encode(confPass);
					userEmail.get().setPassword(encryptPassword);
					userDAO.save(userEmail.get());
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
					userDAO.save(userMobile.get());
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

	public Userdto getServiceProfile(String email) {

		Optional<User> user = Optional.ofNullable(userDAO.findByEmail(email));

		if (user.isPresent()) {
			User userdb = user.get();

			Userdto dto = new Userdto();
			dto.setName(userdb.getName());
			dto.setMobile(userdb.getMobile());
			dto.setEmail(userdb.getEmail());
			dto.setAddress(userdb.getAddress());
			dto.setCity(userdb.getCity());
			dto.setDistrict(userdb.getDistrict());
			dto.setState(userdb.getState());
			dto.setPincodeNo(userdb.getPincodeNo());
			dto.setVerified(userdb.getVerified());
			dto.setUserType(String.valueOf(userdb.getUserType()));
			dto.setStatus(userdb.getStatus());
			dto.setBusinessName(userdb.getBusinessName());
			dto.setBodSeqNo(userdb.getBodSeqNo());
			dto.setRegisteredDate(userdb.getRegisteredDate());
			dto.setUpdatedDate(userdb.getUpdatedDate());
			dto.setServiceCategory(userdb.getServiceCategory());
			return dto;
		}

		return null;

	}
	public List<User> getServicePersonData(String email,String phNo,String location,String status,String category,String fromDate,String toDate) {
		if(fromDate==null && toDate==null&& location==null  && category==null && status!=null|| email!=null || phNo!=null) {
			return userDAO.findByEmailOrMobileOrStatusOrderByRegisteredDateDesc(email, phNo, status);
		}else if(category!=null){
			return userDAO.findByServiceCategory(category);
		}else if(location!=null) {
			return userDAO.findByState(location);
		}
		else {
			return userDAO.findByRegisteredDateBetween(fromDate, toDate);
		}

	}
	
	
	
	public ResponseSpLoginDto loginDetails(String email, String mobile, String password) {

		Optional<ServicePersonLogin> loginDb = Optional.ofNullable((emailLoginRepo.findByEmailOrMobile(email, mobile)));
		ResponseSpLoginDto response=new ResponseSpLoginDto();
		
		if (loginDb.isPresent()) {
			Optional<User> userEmailMobile = Optional.ofNullable(userDAO.findByEmailOrMobile(email, mobile));
			User user = userEmailMobile.get();
			String status = user.getStatus();
			
			
			
			if (userEmailMobile.isPresent()) {
				if (status != null && status.equalsIgnoreCase("active")) {
					if (email != null && mobile==null) {
						if (loginDb.get().getEVerify().equalsIgnoreCase("yes")) {

							if (byCrypt.matches(password, user.getPassword())) {
								String jwtToken = jwtService.generateToken(userEmailMobile.get());
								response.setJwtToken(jwtToken);
								response.setMessage("Login Successful.");
								response.setStatus(true);
								response.setLoginDetails(getServiceProfile(email));
								return response;
							} else {
								response.setMessage("Invalid Password");
								response.setStatus(false);
								return response;
							}
						} else {
							response.setMessage("verify Email");
							response.setStatus(false);
							return response;
						}
					} else if (email == null && mobile !=null) {
						if (loginDb.get().getMobVerify().equalsIgnoreCase("yes")) {
							

							if (byCrypt.matches(password, user.getPassword())) {
								String jwtToken = jwtService.generateToken(userEmailMobile.get());
								response.setJwtToken(jwtToken);
								response.setMessage("Login Successful.");
								response.setStatus(true);
								response.setLoginDetails(getServiceProfile(loginDb.get().getEmail()));
								return response;
							} else {
								response.setMessage("Invalid Password");
								response.setStatus(false);
								return response;
							}
						} else {
							response.setMessage("verify Mobile");
							response.setStatus(false);
							return response;
						}
					}
				} else {
					response.setMessage("Inactive User");
					response.setStatus(false);
					return response;
				}
			}  else {
				response.setMessage("Inactive User");
				response.setStatus(false);
				return response;
			}

		}
		response.setMessage("Invalid User.!");
		response.setStatus(false);
		return response;
	}
	public User getServiceDataProfile(String email) {

		Optional<User> user = Optional.ofNullable(userDAO.findByEmail(email));
		return user.get();
	}
}
