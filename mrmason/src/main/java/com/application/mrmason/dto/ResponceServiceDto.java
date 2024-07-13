package com.application.mrmason.dto;

import lombok.Data;

@Data
public class ResponceServiceDto {
	private String message;
	private boolean status;
    private ServiceCategoryDto data;
}
