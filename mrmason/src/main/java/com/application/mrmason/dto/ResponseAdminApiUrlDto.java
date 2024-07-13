package com.application.mrmason.dto;

import com.application.mrmason.entity.AdminApiUrl;

import lombok.Data;

@Data
public class ResponseAdminApiUrlDto {
	private String message;
	private boolean status;
	private AdminApiUrl data;
}
