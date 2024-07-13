package com.application.mrmason.dto;

import lombok.Data;
@Data
public class ResponseSpWorkersDto {
	private String message;
	private boolean status;
	private SpWorkersDto data;
}
