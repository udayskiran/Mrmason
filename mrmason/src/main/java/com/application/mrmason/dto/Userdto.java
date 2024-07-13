package com.application.mrmason.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Userdto {

	
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
    public String updatedDate;
	public String registeredDate;
    public String verified;
    public String serviceCategory;
    private String userType;
	private String status;
}
