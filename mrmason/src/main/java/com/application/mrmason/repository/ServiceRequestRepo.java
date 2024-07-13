package com.application.mrmason.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.application.mrmason.entity.ServiceRequest;

@Repository
public interface ServiceRequestRepo extends JpaRepository<ServiceRequest, Long>{
	List<ServiceRequest> findByRequestedByOrderByServiceRequestDateDesc(String userId);
	List<ServiceRequest> findByServiceSubCategoryOrderByServiceRequestDateDesc(String serviceName);
	List<ServiceRequest> findByAssetIdOrderByServiceRequestDateDesc(String assetId);
	List<ServiceRequest> findByLocationOrderByServiceRequestDateDesc(String location);
	List<ServiceRequest> findByStatusOrderByServiceRequestDateDesc(String status);
	ServiceRequest findByRequestId(String requestId);

	List<ServiceRequest> findByServiceRequestDateBetween(String startDate, String endDate);

	
}
