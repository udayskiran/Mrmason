package com.application.mrmason.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.ServiceStatusUpate;
@Repository
public interface ServiceStatusUpateRepo extends JpaRepository<ServiceStatusUpate, Long>{

	ServiceStatusUpate findByServiceRequestId(String requestId);

}
