package com.application.mrmason.dto;

import java.util.List;

import com.application.mrmason.entity.AddServices;

import lombok.Data;

@Data
public class ResponseAddServiceGetDto {
	private String message;
	private boolean status;
	private List<AddServices> GetAddServicesData;
}
