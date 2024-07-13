package com.application.mrmason.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mrmason.dto.SpServiceRequestDto;
import com.application.mrmason.entity.SpServiceRequest;
import com.application.mrmason.repository.ServiceRequestRepo;
import com.application.mrmason.repository.SpServiceRequestRepo;
import com.application.mrmason.repository.UserDAO;
import com.application.mrmason.service.SpServiceRequestService;

@Service
public class SpServiceRequestServiceImpl implements SpServiceRequestService{
	@Autowired
	SpServiceRequestRepo requestRepo;
	@Autowired
	ServiceRequestRepo seviceRepo;
	@Autowired
	private UserDAO userRepo;
	
	@Override
	public SpServiceRequestDto addServiceRequest(SpServiceRequest service) {
		if (userRepo.findByBodSeqNo(service.getServicePersonId()) != null) {
			if(seviceRepo.findByRequestId(service.getServiceReqId())!=null) {
				if (requestRepo.findByServiceReqId(service.getServiceReqId()) == null) {
					requestRepo.save(service);
					return getServiceByReqId(service.getServiceReqId());
				}
			}
		
		}
		return null;

	}

	@Override
	public List<SpServiceRequest> getServiceRequest(String serviceReqId,String servicePersonId) {
	
		return requestRepo.findByServiceReqIdOrServicePersonId(serviceReqId, servicePersonId);
	}

	@Override
	public SpServiceRequestDto updateServiceRequest(SpServiceRequest service) {
		String serviceReqId = service.getServiceReqId();
		String assignedBy = service.getAssignedBy();
		String comment = service.getComment();
		String commentUpdatedBy = service.getCommentUpdatedBy();
		String userFeedback = service.getUserFeedback();
		String assignId = service.getAssignId();

		Optional<SpServiceRequest> serviceRequest = Optional.ofNullable(requestRepo.findByServiceReqId(serviceReqId));

		if (serviceRequest.isPresent()) {
			SpServiceRequest spService = serviceRequest.get();

			spService.setServiceReqId(serviceReqId);
			spService.setAssignId(assignId);
			spService.setCommentUpdatedBy(commentUpdatedBy);
			spService.setComment(comment);
			spService.setAssignedBy(assignedBy);
			spService.setUserFeedback(userFeedback);

			requestRepo.save(spService);
			return getServiceByReqId(serviceReqId);
		}
		return null;
	}

	public SpServiceRequestDto getServiceByReqId(String reqId) {

		Optional<SpServiceRequest> serviceRequest = Optional.ofNullable(requestRepo.findByServiceReqId(reqId));
		SpServiceRequest serviceData = serviceRequest.get();
		SpServiceRequestDto request = new SpServiceRequestDto();

		request.setServiceReqId(serviceData.getServiceReqId());
		request.setServicePersonId(serviceData.getServicePersonId());
		request.setAssignId(serviceData.getAssignId());
		request.setComment(serviceData.getComment());
		request.setCommentUpdatedBy(serviceData.getCommentUpdatedBy());
		request.setStatus(serviceData.getStatus());
		request.setAssignedDate(serviceData.getAssignedDate());
		request.setUserFeedback(serviceData.getUserFeedback());
		request.setAssignedBy(serviceData.getAssignedBy());

		return request;

	}
}

