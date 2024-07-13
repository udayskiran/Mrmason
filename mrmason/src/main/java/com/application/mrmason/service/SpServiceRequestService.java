package com.application.mrmason.service;

import java.util.List;

import com.application.mrmason.dto.SpServiceRequestDto;
import com.application.mrmason.entity.SpServiceRequest;

public interface SpServiceRequestService {
	SpServiceRequestDto addServiceRequest(SpServiceRequest service);
	List<SpServiceRequest> getServiceRequest(String serviceReqId,String servicePersonId);
	SpServiceRequestDto updateServiceRequest(SpServiceRequest service);
}
