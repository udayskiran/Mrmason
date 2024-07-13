package com.application.mrmason.service;

import com.application.mrmason.entity.MembershipDetails;

public interface MembershipDetailsService {
	
	String addOrUpdateMembership(MembershipDetails member);
	MembershipDetails getMembership(MembershipDetails member);

}
