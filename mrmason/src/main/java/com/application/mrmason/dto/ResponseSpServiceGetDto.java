package com.application.mrmason.dto;

import java.util.List;

import com.application.mrmason.entity.SpServiceDetails;

import lombok.Data;

@Data
public class ResponseSpServiceGetDto {
	private String message;
	private boolean status;
	private List<SpServiceDetails> data;
}
