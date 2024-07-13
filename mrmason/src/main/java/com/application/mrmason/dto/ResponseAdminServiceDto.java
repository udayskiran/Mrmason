package com.application.mrmason.dto;

import lombok.Data;

@Data
public class ResponseAdminServiceDto {
	private String message;
	private boolean status;
	private AdminServiceNameDto data;
}

