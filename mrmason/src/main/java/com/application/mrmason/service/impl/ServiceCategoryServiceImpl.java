package com.application.mrmason.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mrmason.dto.ServiceCategoryDto;
import com.application.mrmason.entity.ServiceCategory;
import com.application.mrmason.repository.ServiceCategoryRepo;
import com.application.mrmason.service.ServiceCategoryService;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {
	@Autowired
	ServiceCategoryRepo serviceRepo;

	@Override
	public ServiceCategoryDto addServiceCategory(ServiceCategory service) {
		String serviceCat = service.getServiceCategory();
		String serviceSubCat = service.getServiceSubCategory();
		if (serviceRepo.findByServiceCategoryAndServiceSubCategory(serviceCat, serviceSubCat) == null) {
			ServiceCategory data = serviceRepo.save(service);
			return getServiceById(data.getId());

		} else {
			return null;
		}

	}

	@Override
	public List<ServiceCategory> getServiceCategory(String id ,String category,String subCat) {

		if (id != null) {
			Optional<List<ServiceCategory>> user = Optional.ofNullable((serviceRepo.findByIdOrderByCreateDateDesc(id)));
			return user.get();
		} else {
			List<ServiceCategory> user = (serviceRepo.findByServiceCategoryOrServiceSubCategory(category,subCat));
			return user;
		}
	}

	@Override
	public ServiceCategoryDto updateServiceCategory(ServiceCategory service) {
		String id = service.getId();
		String updatedBy = service.getUpdatedBy();
		String category = service.getServiceCategory();
		String subCategory = service.getServiceSubCategory();

		Optional<ServiceCategory> serviceCategory = serviceRepo.findById(id);
		if (serviceCategory.isPresent()) {
			serviceCategory.get().setUpdatedBy(updatedBy);
			serviceCategory.get().setServiceCategory(category);
			serviceCategory.get().setServiceSubCategory(subCategory);

			serviceRepo.save(serviceCategory.get());
			return getServiceById(id);
		}
		return null;
	}

	@Override
	public ServiceCategoryDto getServiceById(String id) {
		if (serviceRepo.findById(id) != null) {
			Optional<ServiceCategory> serviceCat = serviceRepo.findById(id);
			ServiceCategory serviceCatData = serviceCat.get();
			ServiceCategoryDto serviceDto = new ServiceCategoryDto();

			serviceDto.setId(serviceCatData.getId());
			serviceDto.setServiceCategory(serviceCatData.getServiceCategory());
			serviceDto.setServiceSubCategory(serviceCatData.getServiceSubCategory());
			serviceDto.setUpdatedBy(serviceCatData.getUpdatedBy());
			serviceDto.setUpdatedDate(serviceCatData.getUpdatedDate());
			serviceDto.setCreateDate(serviceCatData.getCreateDate());
			serviceDto.setAddedBy(serviceCatData.getAddedBy());

			return serviceDto;

		}
		return null;
	}

	@Override
	public List<ServiceCategory> getServiceCategoryCivil(String category) {

		List<ServiceCategory> user = (serviceRepo.findByServiceCategoryOrderByCreateDateDesc(category));
		return user;

	}

	@Override
	public List<ServiceCategory> getServiceCategoryNonCivil(String category) {

		List<ServiceCategory> user = (serviceRepo.findByServiceCategoryNotOrderByCreateDateDesc(category));
		return user;
	}

}
