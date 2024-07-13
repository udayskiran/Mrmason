package com.application.mrmason.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddServiceDto {


	public String bodSeqNo;
	public String name;
	public String businessName;
	public String mobile;
	public String email;
	public String address;
	public String city;
	public String district;
	public String state;
	public String pincodeNo;

   
	
	public Date registeredDate;
    public String verified;
    public String serviceCategory;
    private String userType;
	
	
    public String serviceSubCategory;
	
	public String userIdServiceId;
	
	public String status;
	
	public String updatedBy;
	
	
	public String updatedDate;
}
