package com.application.mrmason.service;

import com.application.mrmason.dto.ResponseAdminMailDto;
import com.application.mrmason.entity.AdminMail;

public interface AdminMailService {
	
	ResponseAdminMailDto addApiRequest(AdminMail admin);
	ResponseAdminMailDto getApiRequest(String email);
	ResponseAdminMailDto updateApiRequest(AdminMail admin);
}
