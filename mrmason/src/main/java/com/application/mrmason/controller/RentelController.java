package com.application.mrmason.controller;


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

import com.application.mrmason.dto.RentalDto;
import com.application.mrmason.dto.ResponseListRentelDto;
import com.application.mrmason.dto.ResponseRentalDto;
import com.application.mrmason.entity.Rentel;
import com.application.mrmason.service.RentelService;

@RestController
@PreAuthorize("hasAuthority('EC')")
public class RentelController {
	@Autowired
	public RentelService rentService;
	ResponseListRentelDto response=new ResponseListRentelDto();

	@PostMapping("/addRentalData")
	public ResponseEntity<ResponseRentalDto> addRentRequest(@RequestBody Rentel rent) {
		ResponseRentalDto response=new ResponseRentalDto();
		try {
			if (rentService.addRentalReq(rent) != null) {
				
				response.setAddRental(rentService.addRentalReq(rent));
				response.setMessage("Rental asset added successfully..");
				response.setStatus(true);
				return ResponseEntity.ok(response);
			}
			response.setMessage("Invalid User.!");
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	@GetMapping("/getRentalData")
	public ResponseEntity<?> getRentRequest(@RequestParam(required = false) String assetId,@RequestParam(required = false)String userId) {
		try {
			if (rentService.getRentalReq(assetId, userId).isEmpty()) {
				response.setMessage("No data found for the given details.!");
				response.setStatus(true);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setMessage("Rental data fetched successfully.");
			response.setStatus(true);
			response.setData(rentService.getRentalReq(assetId, userId));
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	@PutMapping("/updateRentalData")
	public ResponseEntity<?> updateRentRequest(@RequestBody Rentel rent) {
		try {
			ResponseRentalDto response=new ResponseRentalDto();
			if (rentService.updateRentalReq(rent) != null) {
				response.setAddRental(rentService.updateRentalReq(rent));
				response.setMessage("Rental asset updated successfully..");
				response.setStatus(true);
				return ResponseEntity.ok(response);
			}
			response.setMessage("Invalid User.!");
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
}
