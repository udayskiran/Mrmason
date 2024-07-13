package com.application.mrmason.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mrmason.entity.SPAvailability;
import com.application.mrmason.entity.User;
import com.application.mrmason.repository.SPAvailabilityRepo;
import com.application.mrmason.repository.UserDAO;


@Service
public class SPAvailabilityServiceIml {

	@Autowired
	SPAvailabilityRepo availabilityReo;
	
	@Autowired
	UserDAO userDAO;
	 
	
	public SPAvailability availability(SPAvailability available) {
		
		
		Optional<User> userExists = Optional.ofNullable(userDAO.findByBodSeqNo(available.getBodSeqNo()));
		if(userExists.isPresent()) {
	
			User userDb = userExists.get();
			if(available.getBodSeqNo() != null && userDb.getStatus().equalsIgnoreCase("Active")) {
				
				return availabilityReo.save(available);
			}
		}
		return null;
		
	}
	

	
	public List<SPAvailability> getAvailability(String email,String mobile) {
	    Optional<User> userExists = Optional.ofNullable(userDAO.findByEmailOrMobile(email,mobile));
	  

	    if (userExists.isPresent()) {
	        Optional<List<SPAvailability>> bodSeqNoExists = Optional.ofNullable(availabilityReo.findByBodSeqNo(userExists.get().getBodSeqNo()));
	        if(!bodSeqNoExists.get().isEmpty()) {
	        	return  bodSeqNoExists.get();
	        }else {
	        	return null;
	        }
	       
	       
	}
		return null;
	}



}
