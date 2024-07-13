package com.application.mrmason.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.application.mrmason.entity.SpServiceRequest;
@Repository
public interface SpServiceRequestRepo extends JpaRepository<SpServiceRequest, String>{
	List<SpServiceRequest> findByServiceReqIdOrServicePersonId(String serviceReqId, String servicePersonId);
	SpServiceRequest findByServiceReqId(String serviceId);
}
