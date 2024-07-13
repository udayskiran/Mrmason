package com.application.mrmason.dto;

import java.util.List;

import com.application.mrmason.entity.CustomerAssets;

import lombok.Data;
@Data
public class ResponseListCustomerAssets {
	private String message;
	private boolean status;
	private List<CustomerAssets> data;
}
