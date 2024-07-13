package com.application.mrmason.dto;

import lombok.Data;

@Data
public class ResponseSpServiceRequestDto {
	private String message;
	private boolean status;
	private SpServiceRequestDto data;
}
