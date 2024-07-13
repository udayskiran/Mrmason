package com.application.mrmason.dto;

import java.util.List;

import com.application.mrmason.entity.ServiceRequest;

import lombok.Data;
@Data
public class ResponseListServiceRequestDto {
	private String message;
	private boolean status;
	private List<ServiceRequest> data;
}
