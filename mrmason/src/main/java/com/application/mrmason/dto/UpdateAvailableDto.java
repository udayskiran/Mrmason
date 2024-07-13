package com.application.mrmason.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateAvailableDto {

    public int sepSeqNo;
    public String bodSeqNo;
    public String dateTimeOfUpdate;
    public String email;
    public String availability;
    public List<String> address; // Change address field to a list of strings
 
	public String name;
	public String businessName;
	public String mobile;
	
	
	public String city;
	public String district;
	public String state;
	public String pincodeNo;

    public Date updatedDate;

	
	public Date registeredDate;
    public String verified;
    public String serviceCategory;
    private String userType;
	private String status;
    
    
	
}

	
//	public void setAdressList(List<String> address) {
//        this.address = String.join(",", address);
//    }
//
//    // Helper method to get list of service IDs from comma-separated string
//    public List<String> getAddressList() {
//        return Arrays.asList(this.address.split(","));
//    }

