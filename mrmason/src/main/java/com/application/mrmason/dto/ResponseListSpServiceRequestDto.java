package com.application.mrmason.dto;

import java.util.List;

import com.application.mrmason.entity.SpServiceRequest;

import lombok.Data;
@Data
public class ResponseListSpServiceRequestDto {
	private String message;
	private boolean status;
	private List<SpServiceRequest> data;
}
