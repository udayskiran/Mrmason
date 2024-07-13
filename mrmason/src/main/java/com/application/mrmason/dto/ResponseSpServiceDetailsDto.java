package com.application.mrmason.dto;

import lombok.Data;

@Data
public class ResponseSpServiceDetailsDto {
	private String message;
	private boolean status;
	private SpServiceDetailsDto data;
}
