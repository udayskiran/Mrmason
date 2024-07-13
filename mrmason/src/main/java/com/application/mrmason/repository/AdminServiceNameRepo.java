package com.application.mrmason.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.AdminServiceName;

@Repository
public interface AdminServiceNameRepo extends JpaRepository<AdminServiceName, String>{
	 List<AdminServiceName> findByServiceIdOrServiceNameOrServiceSubCategoryOrderByAddedDateDesc(String serviceId, String serviceName, String serviceSubCat);
	 AdminServiceName findByServiceId(String serviceId);
}

