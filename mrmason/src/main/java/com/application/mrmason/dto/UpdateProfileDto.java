package com.application.mrmason.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileDto {
	private String userid;
	private String userName;
	private String userTown;
	private String userDistrict;
	private String userState;
	private String userPincode;
}
