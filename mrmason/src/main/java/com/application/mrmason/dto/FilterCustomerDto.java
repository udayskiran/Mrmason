package com.application.mrmason.dto;

import lombok.Data;

@Data
public class FilterCustomerDto {
	private String userid;
	private String userEmail;
	private String userMobile;
	private String userState;
	private String fromDate;
	private String toDate;

}
