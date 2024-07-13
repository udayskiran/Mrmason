package com.application.mrmason.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.mrmason.dto.ResponseListServiceRequestDto;
import com.application.mrmason.dto.ResponseServiceReqDto;
import com.application.mrmason.entity.ServiceRequest;
import com.application.mrmason.service.ServiceRequestService;

@RestController
//@PreAuthorize("hasAuthority('EC')")
public class ServiceRequestController {
	@Autowired
	ServiceRequestService reqService;
	ResponseListServiceRequestDto response=new ResponseListServiceRequestDto();
	
	@PostMapping("/addServiceRequest")
	public ResponseEntity<ResponseServiceReqDto> addRequest(@RequestBody ServiceRequest request){
		ResponseServiceReqDto response=new ResponseServiceReqDto();
		try {
			ServiceRequest service=reqService.addRequest(request);
			if(service!=null) {	
				response.setServiceData(service);
				response.setMessage("Service request added successfully..");
				response.setStatus(true);
				return new ResponseEntity<>(response,HttpStatus.OK);
			}else {
				response.setMessage("Invalid User.!");
				response.setStatus(false);
				return new ResponseEntity<>(response,HttpStatus.OK);
			}
		}catch(Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return 	new ResponseEntity<>(response,HttpStatus.OK);
		}
	}
	@GetMapping("/getServiceRequest")
	public ResponseEntity<ResponseListServiceRequestDto> getRequest(@RequestParam(required = false)String userId,
																	@RequestParam(required = false)String assetId,
																	@RequestParam(required = false)String location,
																	@RequestParam(required = false)String serviceSubCategory,
																	@RequestParam(required = false)String email,
																	@RequestParam(required = false)String status,
																	@RequestParam(required = false)String mobile,
																	@RequestParam(required = false)String fromDate,
																	@RequestParam(required = false)String toDate){
		try {

			List<ServiceRequest> serviceReq =reqService.getServiceReq(userId, assetId, location, serviceSubCategory, email, mobile, status, fromDate, toDate);
			if(serviceReq.isEmpty()) {
				response.setMessage("No data found for the given details.!");
				response.setData(serviceReq);
				response.setStatus(true);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setData(serviceReq);
			response.setMessage("ServiceRequest data fetched successfully..");
			response.setStatus(true);
			return ResponseEntity.ok(response);

		}catch(Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	@PutMapping("/updateServiceRequest")
	public ResponseEntity<ResponseServiceReqDto> updateRequest(@RequestBody ServiceRequest request){
		ResponseServiceReqDto response=new ResponseServiceReqDto();
		try {
			ServiceRequest service=reqService.updateRequest(request);
			if(service!=null) {	
				response.setServiceData(service);
				response.setMessage("Service request updated successfully..");
				response.setStatus(true);
				return new ResponseEntity<>(response,HttpStatus.OK);
			}else {
				response.setMessage("Invalid User.!");
				response.setStatus(false);
				return new ResponseEntity<>(response,HttpStatus.OK);
			}
		}catch(Exception e) {
			
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return 	new ResponseEntity<>(response,HttpStatus.OK);
		}
	}
	@PutMapping("/updateServiceStatus")
	public ResponseEntity<ResponseServiceReqDto> updateStatusRequest(@RequestBody ServiceRequest request){
		ResponseServiceReqDto response=new ResponseServiceReqDto();
		try {
			ServiceRequest service=reqService.updateStatusRequest(request);
			if(service!=null) {	
				response.setServiceData(service);
				response.setMessage("Service status updated successfully..");
				response.setStatus(true);
				return new ResponseEntity<>(response,HttpStatus.OK);
			}else {
				response.setMessage("Invalid service ID.!");
				response.setStatus(false);
				return new ResponseEntity<>(response,HttpStatus.OK);
			}
		}catch(Exception e) {
			
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return 	new ResponseEntity<>(response,HttpStatus.OK);
		}
	}
}
