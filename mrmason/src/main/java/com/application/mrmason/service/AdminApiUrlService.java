package com.application.mrmason.service;

import com.application.mrmason.dto.ResponseAdminApiUrlDto;
import com.application.mrmason.dto.ResponseGetApiUrlDto;
import com.application.mrmason.entity.AdminApiUrl;

public interface AdminApiUrlService {
	ResponseAdminApiUrlDto addApiRequest(AdminApiUrl api);
	ResponseGetApiUrlDto getApiRequest(String systemId,String updatedBy,String ip);
	ResponseAdminApiUrlDto updateApiRequest(AdminApiUrl api);
}
