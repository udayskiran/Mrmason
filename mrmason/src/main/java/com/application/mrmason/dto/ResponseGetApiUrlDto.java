package com.application.mrmason.dto;

import java.util.List;

import com.application.mrmason.entity.AdminApiUrl;

import lombok.Data;
@Data
public class ResponseGetApiUrlDto {
	private String message;
	private boolean status;
	private List<AdminApiUrl> data;
}
