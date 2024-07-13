package com.application.mrmason.service;

import java.util.List;
import com.application.mrmason.entity.ServiceRequest;

public interface ServiceRequestService {
	ServiceRequest addRequest(ServiceRequest request);
	List<ServiceRequest> getServiceReq(String userId,String assetId,String location,String serviceName,String email,String mobile,String status,String fromDate,String toDate);
	ServiceRequest updateRequest(ServiceRequest requestData);
	ServiceRequest updateStatusRequest(ServiceRequest requestData);

}
