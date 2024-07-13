package com.application.mrmason.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mrmason.dto.ResponseAdminMailDto;
import com.application.mrmason.entity.AdminMail;
import com.application.mrmason.repository.AdminMailRepo;
import com.application.mrmason.service.AdminMailService;

@Service
public class AdminMailServiceImpl implements AdminMailService {

	@Autowired
	AdminMailRepo mailRepo;
	ResponseAdminMailDto response = new ResponseAdminMailDto();

	@Override
	public ResponseAdminMailDto addApiRequest(AdminMail admin) {
		if (mailRepo.findByEmailid(admin.getEmailid()) == null) {
			AdminMail data = mailRepo.save(admin);
			response.setMessage("Admin mail added successfully.");
			response.setStatus(true);
			response.setData(data);
			return response;
		}
		response.setMessage("A record is already present for this emailID.!");
		response.setStatus(false);
		return response;
	}

	@Override
	public ResponseAdminMailDto getApiRequest(String email) {

		AdminMail data = mailRepo.findByEmailid(email);
		if(data!=null) {
			response.setMessage("Admin mail details retrieved successfully.");
			response.setStatus(true);
			response.setData(data);
			return response;
		}
		response.setMessage("No record found for this requested email ID..!");
		response.setStatus(false);
		return response;

	}

	@Override
	public ResponseAdminMailDto updateApiRequest(AdminMail admin) {
		AdminMail data = mailRepo.findByEmailid(admin.getEmailid());
		if(data!=null) {
			data.setEmailSubject(admin.getEmailSubject());
			data.setPwd(admin.getPwd());
			data.setUpdatedBy(admin.getUpdatedBy());
			data.setMailHost(admin.getMailHost());
			AdminMail updatedData=mailRepo.save(data);
			
			response.setMessage("Admin mail details updated successfully.");
			response.setStatus(true);
			response.setData(updatedData);
			return response;
		}
		response.setMessage("No record found for this requested email ID..!");
		response.setStatus(false);
		return response;

	}

}
