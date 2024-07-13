package com.application.mrmason.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.AdminApiUrl;
@Repository
public interface AdminApiUrlRepo extends JpaRepository<AdminApiUrl, String>{
	List<AdminApiUrl> findBySystemIdOrUpdatedByOrIp(String systemId, String updatedBy, String ip);
	AdminApiUrl findBySystemIdAndIp(String systemId, String ip);
	AdminApiUrl findBySystemId(String systemId);
}
