package com.application.mrmason.dto;

import com.application.mrmason.entity.User;

import lombok.Data;
@Data
public class ResponseUserUpdateDto {
	private String message;
	private boolean status;
	private User userData;
}
