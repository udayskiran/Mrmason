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

import com.application.mrmason.dto.ResponseAdminApiUrlDto;
import com.application.mrmason.dto.ResponseGetApiUrlDto;
import com.application.mrmason.entity.AdminApiUrl;
import com.application.mrmason.service.AdminApiUrlService;

@RestController
@PreAuthorize("hasAuthority('Adm')")
public class AdminApiUrlController {
	@Autowired
	AdminApiUrlService apiService;
	
	ResponseAdminApiUrlDto response=new ResponseAdminApiUrlDto();
	ResponseGetApiUrlDto response2=new ResponseGetApiUrlDto();

	@PostMapping("/addAdminApiUrl")
	public ResponseEntity<ResponseAdminApiUrlDto> newAdminAsset(@RequestBody AdminApiUrl api) {
		try {
			ResponseAdminApiUrlDto response=apiService.addApiRequest(api);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@GetMapping("/getAdminApiUrl")
	public ResponseEntity<ResponseGetApiUrlDto> getAssetDetails(@RequestParam(required = false) String systemId,@RequestParam(required = false) String updatedBy,@RequestParam(required = false) String ip) {
		try {
			ResponseGetApiUrlDto response2=apiService.getApiRequest(systemId, updatedBy, ip);
			return new ResponseEntity<>(response2, HttpStatus.OK);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response2, HttpStatus.OK);
		}

	}

	@PutMapping("/updateAdminApiUrl")
	public ResponseEntity<ResponseAdminApiUrlDto> updateAssetDetails(@RequestBody AdminApiUrl api) {
		try {
			ResponseAdminApiUrlDto response=apiService.updateApiRequest(api);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
}
