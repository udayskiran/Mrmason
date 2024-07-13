package com.application.mrmason.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mrmason.dto.ResponseAdminApiUrlDto;
import com.application.mrmason.dto.ResponseGetApiUrlDto;
import com.application.mrmason.entity.AdminApiUrl;
import com.application.mrmason.repository.AdminApiUrlRepo;
import com.application.mrmason.service.AdminApiUrlService;
@Service
public class AdminApiUrlServiceImpl implements AdminApiUrlService{

	@Autowired
	AdminApiUrlRepo apiRepo;

	ResponseAdminApiUrlDto response=new ResponseAdminApiUrlDto();

	ResponseGetApiUrlDto response2=new ResponseGetApiUrlDto();
	
	@Override
	public ResponseAdminApiUrlDto addApiRequest(AdminApiUrl api) {
		if(apiRepo.findBySystemIdAndIp(api.getSystemId(),api.getIp())==null) {
			AdminApiUrl apiData= apiRepo.save(api);
			response.setMessage("Admin api data added successfully.");
			response.setStatus(true);
			response.setData(apiData);
			return response;
		}
		response.setMessage("An api url already present for this SystemId.!");
		response.setStatus(false);
		return response;
		
	}

	@Override
	public ResponseGetApiUrlDto getApiRequest(String systemId,String updatedBy,String ip) {
		List<AdminApiUrl> data=apiRepo.findBySystemIdOrUpdatedByOrIp(systemId, updatedBy, ip);
		if(!data.isEmpty()) {
			response2.setMessage("Admin API data fetched successfully.");
			response2.setStatus(true);
			response2.setData(data);
			return response2;
		}
		response2.setMessage("No API data found for the given details.!");
		response2.setStatus(true);
		response2.setData(data);
		return response2;
	}

	@Override
	public ResponseAdminApiUrlDto updateApiRequest(AdminApiUrl api) {
		AdminApiUrl apiData=apiRepo.findBySystemId(api.getSystemId());
		if(apiData!=null) {
			apiData.setUrl(api.getUrl());
			apiData.setUpdatedBy(api.getUpdatedBy());
			AdminApiUrl changes=apiRepo.save(apiData);
			response.setMessage("Admin api data updated successfully.");
			response.setStatus(true);
			response.setData(changes);
			return response;
		}
		response.setMessage("Invalid SystemId.!");
		response.setStatus(false);
		return response;
	}
}
