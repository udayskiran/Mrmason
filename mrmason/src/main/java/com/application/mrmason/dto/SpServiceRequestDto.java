package com.application.mrmason.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpServiceRequestDto {
	private String serviceReqId;
	private String servicePersonId;
	private String assignId;
	private String status;
	private String comment;
	private String commentUpdatedBy;
	private String userFeedback;
	private String assignedDate;
	private String assignedBy;
}