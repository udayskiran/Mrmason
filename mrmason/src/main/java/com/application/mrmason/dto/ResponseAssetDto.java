package com.application.mrmason.dto;

import lombok.Data;

@Data
public class ResponseAssetDto {
	private String message;
	private boolean status;
	private CustomerAssetDto addAsset;
}
