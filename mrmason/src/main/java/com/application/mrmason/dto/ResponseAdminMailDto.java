package com.application.mrmason.dto;

import com.application.mrmason.entity.AdminMail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAdminMailDto {
	private String message;
	private boolean status;
	private AdminMail data;
}
