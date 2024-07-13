package com.application.mrmason.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.mrmason.dto.ResponseMembership;
import com.application.mrmason.entity.MembershipDetails;
import com.application.mrmason.repository.MembershipDetailsRepo;
import com.application.mrmason.repository.MembershipRepo;
import com.application.mrmason.service.MembershipDetailsService;

@RestController
@PreAuthorize("hasAuthority('EC')")
public class MembershipController {

	@Autowired
	public MembershipDetailsService memService;
	@Autowired
	public MembershipRepo memberRepo;
	@Autowired
	public MembershipDetailsRepo memDetailsRepo;

	@PostMapping("/addMembership")
	public ResponseEntity<?> addMembership(@RequestBody MembershipDetails member) {
		ResponseMembership response = new ResponseMembership();
		try {

			String addedMember = memService.addOrUpdateMembership(member);

			if (addedMember == "added") {
				// If membership was successfully added, return a custom response
				response.setMembership(memService.getMembership(member));
				response.setMessage("Membership successfully added.");
				response.setStatus(true);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else if (addedMember == "renewel") {
				// If membership addition failed, return an error response
				response.setMembership(memService.getMembership(member));
				response.setMessage("Membership successfully renewed.");
				response.setStatus(true);
				return new ResponseEntity<>(response, HttpStatus.OK);

			} else if (addedMember == "present") {
				return new ResponseEntity<>("A Membership is in active for this asset.!!", HttpStatus.OK);
			}
			response.setMessage("Failed to add membership, Invalid User");
			response.setStatus(false);
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
		}

	}
}
