package com.application.mrmason.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterCustomerAndUser {
	private String userEmail;
	private String userMobile;
	private String userState;
	private String fromDate;
	private String toDate;
	private String status;
	public String serviceCategory;
	
	
}
