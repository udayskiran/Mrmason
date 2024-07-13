package com.application.mrmason.dto;

import com.application.mrmason.entity.AddServices;

import lombok.Data;

@Data
public class ResponseAddServiceDto {
	private String message;
	private boolean status;
	private AddServices AddServicesData;
}
