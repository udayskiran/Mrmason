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

import com.application.mrmason.dto.ResponseAdminAmcDto;
import com.application.mrmason.dto.ResponseListAdminAmcRate;
import com.application.mrmason.entity.AdminAmcRate;
import com.application.mrmason.service.AdminAmcRateService;
@RestController
@PreAuthorize("hasAuthority('Adm')")
public class AdminAmcRateController {
	@Autowired
	public AdminAmcRateService adminService;
	ResponseListAdminAmcRate response=new ResponseListAdminAmcRate();
	@PostMapping("/addAdminAmc")
	public ResponseEntity<ResponseAdminAmcDto> addRentRequest(@RequestBody AdminAmcRate amc) {
		ResponseAdminAmcDto response=new ResponseAdminAmcDto();
		try {
			if (adminService.addAdminamc(amc) != null) {
				response.setAdminAmcRates(adminService.addAdminamc(amc));
				response.setMessage("AMC added successfully..");
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
	
	@GetMapping("/getAdminAmcRates")
	public ResponseEntity<ResponseListAdminAmcRate> getAssetDetails(@RequestParam(required = false) String amcId,@RequestParam(required = false)String planId,@RequestParam(required = false)String assetSubCat,@RequestParam(required = false)String assetModel,@RequestParam(required = false)String assetBrand) {
		try {
			List<AdminAmcRate> entity = adminService.getAmcRates(amcId, planId, assetSubCat, assetModel, assetBrand);
			if (entity.isEmpty()) {
				response.setMessage("No data found for the given details.!");
				response.setStatus(true);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setMessage("Amc details fetched successfully.");
			response.setStatus(true);
			response.setData(entity);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	@PutMapping("/updateAdminAmcRates")
	public ResponseEntity<ResponseAdminAmcDto> updateAssetDetails(@RequestBody AdminAmcRate updateAmc) {
		ResponseAdminAmcDto response=new ResponseAdminAmcDto();
		try {

			if (adminService.updateAmcRates(updateAmc) != null) {
				
				response.setAdminAmcRates(adminService.updateAmcRates(updateAmc));
				response.setMessage("Admin Amc Rates updated successfully..");
				response.setStatus(true);
				return new ResponseEntity<>(response, HttpStatus.OK);
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
