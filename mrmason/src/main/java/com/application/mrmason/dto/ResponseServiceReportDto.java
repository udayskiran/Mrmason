package com.application.mrmason.dto;

import java.util.List;

import com.application.mrmason.entity.AddServices;
import com.application.mrmason.entity.SPAvailability;

import lombok.Data;

@Data
public class ResponseServiceReportDto {
	String message;
	private boolean status;
	Userdto regData;
	List<AddServices> servData;
	List<SPAvailability> availData;
	 
	  
}

