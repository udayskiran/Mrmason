package com.application.mrmason.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mrmason.dto.AdminServiceNameDto;
import com.application.mrmason.entity.AdminServiceName;
import com.application.mrmason.repository.AdminServiceNameRepo;
import com.application.mrmason.service.AdminServiceNameService;

@Service
public class AdminServiceNameServiceImpl implements AdminServiceNameService{
	@Autowired
	AdminServiceNameRepo serviceRepo;

	@Override
	public AdminServiceNameDto addAdminServiceNameRequest(AdminServiceName service) {
		if(serviceRepo.findByServiceId(service.getServiceId())==null) {
			serviceRepo.save(service);
			return getServiceById(service.getServiceId());
		}
		return null;

	}
	@Override
	public List<AdminServiceName> getAdminServiceDetails(String serviceId,String serviceName,String serviceSubCat) {

		if (serviceId != null || serviceName != null || serviceSubCat != null) {
			return serviceRepo.findByServiceIdOrServiceNameOrServiceSubCategoryOrderByAddedDateDesc(serviceId, serviceName, serviceSubCat);
		} else {
			return null;
		}
	}

	@Override
	public AdminServiceNameDto updateAdminServiceDetails(AdminServiceName service) {
		String id = service.getServiceId();
		String addedBy = service.getAddedBy();
		String serviceName = service.getServiceName();
		String subCategory = service.getServiceSubCategory();

		Optional<AdminServiceName> adminService = Optional.ofNullable(serviceRepo.findByServiceId(id));
		if (adminService.isPresent()) {
			adminService.get().setAddedBy(addedBy);
			adminService.get().setServiceName(serviceName);
			adminService.get().setServiceSubCategory(subCategory);

			serviceRepo.save(adminService.get());
			return getServiceById(id);
		}
		return null;
	}

	public AdminServiceNameDto getServiceById(String id) {
		if (serviceRepo.findByServiceId(id) != null) {
			Optional<AdminServiceName> serviceCat = Optional.ofNullable(serviceRepo.findByServiceId(id));
			AdminServiceName serviceCatData = serviceCat.get();
			AdminServiceNameDto serviceDto = new AdminServiceNameDto();

			serviceDto.setServiceId(serviceCatData.getServiceId());
			serviceDto.setServiceSubCat(serviceCatData.getServiceSubCategory());
			serviceDto.setAddedBy(serviceCatData.getAddedBy());
			serviceDto.setAddedDate(serviceCatData.getAddedDate());
			serviceDto.setServiceName(serviceCatData.getServiceName());

			return serviceDto;

		}
		return null;
	}

}
