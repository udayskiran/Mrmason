package com.application.mrmason.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.mrmason.dto.ResponceServiceDto;
import com.application.mrmason.dto.ResponseListServiceCatDto;
import com.application.mrmason.dto.ServiceCategoryDto;
import com.application.mrmason.entity.ServiceCategory;
import com.application.mrmason.service.ServiceCategoryService;

@RestController

public class ServiceCategoryController {

	@Autowired
	public ServiceCategoryService categoryService;

	ResponseListServiceCatDto response2 = new ResponseListServiceCatDto();

	@PreAuthorize("hasAuthority('Adm')")
	@PostMapping("/addServiceCategory")
	public ResponseEntity<?> addRentRequest(@RequestBody ServiceCategory service) {
		ResponceServiceDto response = new ResponceServiceDto();
		try {
			ServiceCategoryDto data = categoryService.addServiceCategory(service);
			if (data != null) {
				response.setData(data);
				response.setMessage("Service category added successfully..");
				response.setStatus(true);
				return ResponseEntity.ok(response);
			}
			response.setMessage("A service is already present wih this sub category.!");
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
	}

	@PreAuthorize("hasAuthority('Adm')")
	@GetMapping("/getServiceCategory")
	public ResponseEntity<ResponseListServiceCatDto> getServiceCategory(@RequestParam(required = false)String id ,@RequestParam(required = false)String serviceCategory,@RequestParam(required = false)String serviceSubCategory) {
		try {
			List<ServiceCategory> entity = categoryService.getServiceCategory(id, serviceCategory, serviceSubCategory);

			if (!entity.isEmpty()) {
				response2.setMessage("Service data fetched successfully.!");
				response2.setStatus(true);
				response2.setData(entity);
				return new ResponseEntity<>(response2, HttpStatus.OK);
			}
			response2.setMessage("No data found for the given details.!");
			response2.setStatus(true);
			response2.setData(entity);
			return new ResponseEntity<>(response2, HttpStatus.OK);

		} catch (Exception e) {
			response2.setMessage(e.getMessage());
			response2.setStatus(false);
			return new ResponseEntity<>(response2, HttpStatus.OK);
		}

	}

	@GetMapping("/getServiceCategory/civil/{serviceCategory}")
	public ResponseEntity<ResponseListServiceCatDto> getServiceCategoryCivil(@PathVariable String serviceCategory) {
		try {
			List<ServiceCategory> entity = categoryService.getServiceCategoryCivil(serviceCategory);

			response2.setMessage("Civil service data fetched successfully.!");
			response2.setStatus(true);
			response2.setData(entity);
			return new ResponseEntity<>(response2, HttpStatus.OK);

		} catch (Exception e) {
			response2.setMessage(e.getMessage());
			response2.setStatus(false);
			return new ResponseEntity<>(response2, HttpStatus.OK);
		}

	}

	@GetMapping("/getServiceCategory/nonCivil/{serviceCategory}")
	public ResponseEntity<ResponseListServiceCatDto> getServiceCategoryNonCivil(@PathVariable String serviceCategory) {
		try {
			List<ServiceCategory> entity = categoryService.getServiceCategoryNonCivil(serviceCategory);

			response2.setMessage("Non-Civil service data fetched successfully.!");
			response2.setStatus(true);
			response2.setData(entity);
			return new ResponseEntity<>(response2, HttpStatus.OK);

		} catch (Exception e) {
			response2.setMessage(e.getMessage());
			response2.setStatus(false);
			return new ResponseEntity<>(response2, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAuthority('Adm')")
	@PutMapping("/updateServiceCategory")
	public ResponseEntity<?> updateServiceCategory(@RequestBody ServiceCategory service) {
		ResponceServiceDto response = new ResponceServiceDto();
		try {
			ServiceCategoryDto data = categoryService.updateServiceCategory(service);
			if (data != null) {

				response.setData(data);
				response.setMessage("Service category updated successfully..");
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
