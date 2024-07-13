package com.application.mrmason.controller;

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

import com.application.mrmason.dto.ResponseGetWorkerDto;
import com.application.mrmason.dto.ResponseSpWorkersDto;
import com.application.mrmason.dto.SpWorkersDto;
import com.application.mrmason.entity.SpWorkers;
import com.application.mrmason.entity.User;
import com.application.mrmason.repository.SpWorkersRepo;
import com.application.mrmason.repository.UserDAO;
import com.application.mrmason.service.SpWorkersService;
import com.application.mrmason.service.impl.UserService;
@RestController
@PreAuthorize("hasAuthority('Developer')")
public class SpWorkersController {
	@Autowired
	SpWorkersService service;
	@Autowired
	SpWorkersRepo repo;
	@Autowired
	UserService userService;
	@Autowired
	UserDAO userRepo;
	ResponseSpWorkersDto response=new ResponseSpWorkersDto();
	ResponseGetWorkerDto response2=new ResponseGetWorkerDto();
	@PostMapping("/addWorkers")
	public ResponseEntity<ResponseSpWorkersDto> addService(@RequestBody SpWorkers worker) {
		
		try {
	
			String addedService = service.addWorkers(worker);
			if (addedService == "added") {
				response.setMessage("Worker added successfully");
				response.setData(service.getDetails(worker.getWorkPhoneNum()));
				response.setStatus(true);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}else if(addedService=="notUnique") {
				response.setMessage("Mobile number is already exists.!");
				response.setStatus(false);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			response.setMessage("Failed to add worker, Invalid SPid..!");
			response.setStatus(false);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	@GetMapping("/getWorkerDetails")
	public ResponseEntity<ResponseGetWorkerDto> getAssetDetails(@RequestParam(required = false)String spId,@RequestParam(required = false)String workerId,@RequestParam(required = false)String phno,@RequestParam(required = false)String location,@RequestParam(required = false)String workerAvail) {
		
		try {
			List<SpWorkersDto> entity = service.getWorkers(spId, workerId, phno, location, workerAvail);
			if(!entity.isEmpty()) {
				response2.setMessage("Worker details retrived successfully..");
				response2.setWorkersData(entity);
				response2.setStatus(true);
				User data=userRepo.findByBodSeqNo(spId);
				response2.setUserData(userService.getServiceProfile(data.getEmail()));
				return new ResponseEntity<>(response2, HttpStatus.OK);
			}else {
				response2.setMessage("No data found for details provided.!");
				response2.setStatus(true);
				response2.setWorkersData(entity);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			}
			

		} catch (Exception e) {
			response2.setStatus(false);
			response2.setMessage(e.getMessage());
			return new ResponseEntity<>(response2, HttpStatus.OK);
		}

	}

	@PutMapping("/updateWorkerDetails")
	public ResponseEntity<ResponseSpWorkersDto> updateAssetDetails(@RequestBody SpWorkersDto worker) {
		ResponseSpWorkersDto response=new ResponseSpWorkersDto();
		try {
			String spData= service.updateWorkers(worker);
			if (spData=="updated") {
				SpWorkers workerData= repo.findByWorkerIdAndServicePersonId(worker.getWorkerId(), worker.getServicePersonId());
				
				response.setData(service.getDetails(workerData.getWorkPhoneNum()));
				response.setMessage("Worker details updated successfully..");
				response.setStatus(true);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setMessage("Invalid User.!");
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
}