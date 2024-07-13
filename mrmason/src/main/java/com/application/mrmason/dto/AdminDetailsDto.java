package com.application.mrmason.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDetailsDto {
	private long id;
	private String adminType;
	private String adminName;
	private String mobile;
	private String email;	
	private String status;
	private String regDate;
}
