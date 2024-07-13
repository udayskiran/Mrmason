package com.application.mrmason.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.AddServices;

@Repository
public interface AddServiceRepo extends JpaRepository<AddServices, String> {

	List<AddServices> findByServiceSubCategory(String serviceSubCategory);

	List<AddServices> findByBodSeqNo(String bodSeqNo);
	AddServices findByUserIdServiceId(String userIdServiceId);
	List<AddServices> findByUserIdServiceIdOrderByUpdateDateFormatDesc(String userIdServiceId);
	List<AddServices> findByServiceSubCategoryAndBodSeqNo(String serviceSubCategory, String bodSeqNo);
	@Query("SELECT a FROM AddServices a WHERE a.userIdServiceId = :userIdServiceId")
	List<AddServices> getUserIdServiceIdDetails(String userIdServiceId);

}
