package com.application.mrmason.dto;

import com.application.mrmason.entity.AdminAsset;

import lombok.Data;

@Data
public class ResponseAdminAssets {
	private String message;
	private boolean status;
	private AdminAsset addAsset;
}
