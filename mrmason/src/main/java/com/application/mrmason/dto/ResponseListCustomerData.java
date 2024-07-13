package com.application.mrmason.dto;

import java.util.List;

import com.application.mrmason.entity.CustomerRegistration;

import lombok.Data;

@Data
public class ResponseListCustomerData {
	private String message;
	private boolean status;
	private List<CustomerRegistration> data;
}
