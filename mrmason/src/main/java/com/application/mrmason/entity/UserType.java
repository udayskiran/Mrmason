package com.application.mrmason.entity;

public enum UserType {
    Developer,
    EC,
    Adm,
    worker;
	
    public static UserType fromString(String userType) {
        switch (userType.toLowerCase()) {
            case "worker":
                return worker;
            case "developer":
                return Developer;
            case "adm":
                return Adm;
            case "ec":
            	return EC;
            default:
                throw new IllegalArgumentException("Unknown user type: " + userType);
        }
    }
}
