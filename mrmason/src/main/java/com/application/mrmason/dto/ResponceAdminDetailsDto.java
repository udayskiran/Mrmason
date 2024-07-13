package com.application.mrmason.dto;

import lombok.Data;
@Data
public class ResponceAdminDetailsDto {
	private String message;
	private boolean status;
	private String jwtToken;
    private AdminDetailsDto data;
}
