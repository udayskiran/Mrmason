package com.application.mrmason.dto;

import com.application.mrmason.entity.ServiceRequest;

import lombok.Data;

@Data
public class ResponseServiceReqDto {
	private String message;
	private boolean status;
	private ServiceRequest ServiceData;
}
