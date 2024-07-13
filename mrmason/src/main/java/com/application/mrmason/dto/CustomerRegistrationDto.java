package com.application.mrmason.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerRegistrationDto {
	
	private long id;
	private String userid;
	private String userEmail;
	private String userMobile;
	private String usertype;
	private String userName;
	private String userTown;
	private String userDistrict;
	private String userState;
	private String userPincode;
	private String regDate;

}
