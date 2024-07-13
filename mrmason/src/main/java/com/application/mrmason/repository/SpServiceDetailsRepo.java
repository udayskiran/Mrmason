package com.application.mrmason.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.SpServiceDetails;
@Repository
public interface SpServiceDetailsRepo extends JpaRepository<SpServiceDetails, String>{
	List<SpServiceDetails> findByUserIdOrServiceTypeOrUserServicesId(String userId, String serviceType, String userServiceId);
	SpServiceDetails findByUserServicesId(String userServiceId);
}
