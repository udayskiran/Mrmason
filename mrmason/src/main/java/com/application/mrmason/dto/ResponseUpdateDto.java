package com.application.mrmason.dto;

import lombok.Data;
@Data
public class ResponseUpdateDto {
	private String message;
	private boolean status;
	private CustomerRegistrationDto updateProfile;
}
