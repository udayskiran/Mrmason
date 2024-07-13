package com.application.mrmason.dto;

import lombok.Data;

@Data
public class ResponseListAdminDetailsDto {
	private String message;
	private boolean status;
	private AdminDetailsDto data;
}
