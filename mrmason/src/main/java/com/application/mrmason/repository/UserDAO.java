package com.application.mrmason.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, String> {

	boolean existsByEmail(String email);

	boolean existsByMobile(String mobile);

	User findByEmail(String email);
	User findByMobile(String mobile);
	User findByEmailOrMobile(String email, String mobile);

	User findByBodSeqNo(String bodSeqNo);

	User findByAddress(String address);
	
	List<User>  findByEmailOrMobileOrStatusOrderByRegisteredDateDesc(String email, String mobile,String status);
	
	List<User> findByServiceCategory(String serviceCategory);
	@Query("SELECT cr FROM User cr WHERE cr.registeredDate BETWEEN :startDate AND :endDate")
	List<User> findByRegisteredDateBetween(String startDate, String endDate);
	List<User> findByState(String state);

}
