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
import com.application.mrmason.dto.ResponseAdminMailDto;
import com.application.mrmason.entity.AdminMail;
import com.application.mrmason.service.AdminMailService;

@RestController
@PreAuthorize("hasAuthority('Adm')")
public class AdminMailController {
	@Autowired
	AdminMailService apiService;
	
	ResponseAdminMailDto response=new ResponseAdminMailDto();

	@PostMapping("/addAdminMail")
	public ResponseEntity<ResponseAdminMailDto> newAdminAsset(@RequestBody AdminMail api) {
		try {
			ResponseAdminMailDto response=apiService.addApiRequest(api);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@GetMapping("/getAdminMail")
	public ResponseEntity<ResponseAdminMailDto> getAssetDetails(@RequestParam(required = false) String email) {
		try {
			ResponseAdminMailDto response2=apiService.getApiRequest(email);
			return new ResponseEntity<>(response2, HttpStatus.OK);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	@PutMapping("/updateAdminMail")
	public ResponseEntity<ResponseAdminMailDto> updateAssetDetails(@RequestBody AdminMail api) {
		try {
			ResponseAdminMailDto response=apiService.updateApiRequest(api);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
}
