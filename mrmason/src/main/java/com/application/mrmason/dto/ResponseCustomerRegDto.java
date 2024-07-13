package com.application.mrmason.dto;

import lombok.Data;
@Data
public class ResponseCustomerRegDto {
	private String message;
	private boolean status;
	private CustomerRegistrationDto data;
}
