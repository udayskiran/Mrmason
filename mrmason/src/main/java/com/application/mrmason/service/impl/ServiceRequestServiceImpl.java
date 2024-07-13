package com.application.mrmason.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mrmason.entity.CustomerAssets;
import com.application.mrmason.entity.CustomerRegistration;
import com.application.mrmason.entity.ServiceRequest;
import com.application.mrmason.entity.ServiceStatusUpate;
import com.application.mrmason.repository.CustomerAssetsRepo;
import com.application.mrmason.repository.CustomerRegistrationRepo;
import com.application.mrmason.repository.ServiceRequestRepo;
import com.application.mrmason.repository.ServiceStatusUpateRepo;
import com.application.mrmason.service.ServiceRequestService;

@Service
public class ServiceRequestServiceImpl implements ServiceRequestService{
    @Autowired
	public ServiceRequestRepo requestRepo;
    @Autowired
    public CustomerAssetsRepo assetRepo;
    @Autowired
	public CustomerRegistrationRepo repo;
    @Autowired
    ServiceStatusUpateRepo statusRepo;
	
    ServiceStatusUpate statusUpdate=new ServiceStatusUpate();
    @Autowired
    ModelMapper model;


	@Override
	public ServiceRequest addRequest(ServiceRequest requestData) {
		Optional<CustomerAssets> serviceRequestData=assetRepo.findByUserIdAndAssetId(requestData.getRequestedBy(), requestData.getAssetId());
		if(serviceRequestData.isPresent()) {
			ServiceRequest service= requestRepo.save(requestData);
			statusUpdate.setServiceRequestId(service.getRequestId());
			statusUpdate.setUpdatedBy(service.getRequestedBy());
			statusRepo.save(statusUpdate);
			return service;
		}
		return null;
	}

//	@Override
//	public List<ServiceRequest>  getServiceReq(String userId,String assetId,String location,String serviceName,String email,String mobile,String status,String fromDate,String toDate) {
//
//		if(userId!=null && assetId==null && location==null && serviceName==null && email==null && status==null&& mobile==null) {
//			List<ServiceRequest> user=requestRepo.findByRequestedByOrderByServiceRequestDateDesc(userId);
//			return user;
//		}else if(userId==null && assetId!=null && location==null && serviceName==null && email==null && status==null&& mobile==null) {
//			Optional<List<ServiceRequest>> user=Optional.of((requestRepo.findByAssetIdOrderByServiceRequestDateDesc(assetId)));
//			return user.get();
//		}else if(userId==null && assetId==null && location!=null && serviceName==null &&  email==null && status==null&& mobile==null) {
//			Optional<List<ServiceRequest>> user=Optional.of((requestRepo.findByLocationOrderByServiceRequestDateDesc(location)));
//			return user.get();
//		}else if(userId==null && assetId==null && location==null && serviceName!=null &&  email==null && status==null&& mobile==null) {
//			Optional<List<ServiceRequest>> user=Optional.of((requestRepo.findByServiceSubCategoryOrderByServiceRequestDateDesc(serviceName)));
//			return user.get();
//		}else if(userId==null && assetId==null && location==null && serviceName==null &&  email!=null || mobile!=null && status==null ) {
//			Optional<CustomerRegistration> existedById = Optional.ofNullable(repo.findByUserEmailOrUserMobile(email, mobile));
//			if(existedById.isPresent()) {
//				Optional<List<ServiceRequest>> user=Optional.of((requestRepo.findByRequestedByOrderByServiceRequestDateDesc(existedById.get().getUserid())));
//				return user.get();
//			}
//		}else if(userId==null && assetId==null && location==null && serviceName==null &&  email==null && status!=null&& mobile==null) {
//			Optional<List<ServiceRequest>> user=Optional.of((requestRepo.findByStatusOrderByServiceRequestDateDesc(status)));
//			return user.get();
//		}else if(userId==null && assetId==null && location==null && serviceName==null &&  email==null && status==null&& mobile==null&&fromDate!=null&& toDate!=null) {
//			Optional<List<ServiceRequest>> user=Optional.of((requestRepo.findByServiceRequestDateBetween(fromDate, toDate)));
//			return user.get();
//		}
//		return null;
//	}



	@Override
	public List<ServiceRequest> getServiceReq(String userId, String assetId, String location, String serviceName, String email, String mobile, String status, String fromDate, String toDate) {
		if (userId != null) {
			return requestRepo.findByRequestedByOrderByServiceRequestDateDesc(userId);
		} else if (assetId != null) {
			return requestRepo.findByAssetIdOrderByServiceRequestDateDesc(assetId);
		} else if (location != null) {
			return requestRepo.findByLocationOrderByServiceRequestDateDesc(location);
		} else if (serviceName != null) {
			return requestRepo.findByServiceSubCategoryOrderByServiceRequestDateDesc(serviceName);
		} else if (email != null || mobile != null) {
			CustomerRegistration customer = repo.findByUserEmailOrUserMobile(email, mobile);
			if (customer != null) {
				return requestRepo.findByRequestedByOrderByServiceRequestDateDesc(customer.getUserid());
			}
		} else if (status != null) {
			return requestRepo.findByStatusOrderByServiceRequestDateDesc(status);
		} else if (fromDate != null && toDate != null) {
			return requestRepo.findByServiceRequestDateBetween(fromDate, toDate);
		}
		return Collections.emptyList();
	}







	@Override
	public ServiceRequest updateRequest(ServiceRequest requestData) {
		ServiceRequest serviceRequestData=requestRepo.findByRequestId(requestData.getRequestId());
		if(serviceRequestData!=null) {
//			ServiceRequest service= model.map(serviceRequestData, ServiceRequest.class);
			serviceRequestData.setDescription(requestData.getDescription());
			serviceRequestData.setLocation(requestData.getLocation());
			serviceRequestData.setServiceSubCategory(requestData.getServiceSubCategory());
			return requestRepo.save(serviceRequestData);
		}
		return null;
	}
	@Override
	public ServiceRequest updateStatusRequest(ServiceRequest requestData) {
		ServiceRequest serviceRequestData=requestRepo.findByRequestId(requestData.getRequestId());
		if(serviceRequestData!=null) {
			ServiceStatusUpate update=statusRepo.findByServiceRequestId(requestData.getRequestId());
			if(update!=null) {
				serviceRequestData.setStatus(requestData.getStatus());
				ServiceRequest service= requestRepo.save(serviceRequestData);
				
				update.setStatus(requestData.getStatus());
				statusRepo.save(update);
				return service;
			}
			
		}
		return null;
	}
	
}
