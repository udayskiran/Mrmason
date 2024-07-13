package com.application.mrmason.dto;

import java.util.Date;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddServiceGetDto {

    public String serviceId;
	
	
	public String serviceSubCategory;
	
	public String userIdServiceId;
	
	public String status;
	
	public String bodSeqNo;
	
	
	public String updatedBy;
	
	
	public Date updatedDate;

	public String email;
}
