package com.application.mrmason.dto;

import com.application.mrmason.entity.MembershipDetails;

import lombok.Data;
@Data
public class ResponseMembership {
	private String message;
	private boolean status;
	private MembershipDetails membership;
}
