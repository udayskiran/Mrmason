package com.application.mrmason.dto;

import java.util.List;

import com.application.mrmason.entity.SPAvailability;

import lombok.Data;

@Data
public class ResponseSPAvailabilityUpdateDto {

	private String message;
	private boolean status;
	private SPAvailability getData;
}
