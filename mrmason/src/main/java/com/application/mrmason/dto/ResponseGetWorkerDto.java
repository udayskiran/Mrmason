package com.application.mrmason.dto;

import java.util.List;

import lombok.Data;
@Data
public class ResponseGetWorkerDto {
	private String message;
	private boolean status;
	private List<SpWorkersDto> workersData;
	private Userdto userData;
}