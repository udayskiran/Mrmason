package com.application.mrmason.dto;

import lombok.Data;

@Data
public class ResponseLoginDto {
	private String message;
	private boolean status;
	private String jwtToken;
	private CustomerRegistrationDto loginDetails;
	
}
