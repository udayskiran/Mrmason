package com.application.mrmason.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.CustomerRegistration;
@Repository
public interface CustomerRegistrationRepo extends JpaRepository<CustomerRegistration, Long>{
	CustomerRegistration findByUserEmailOrUserMobile(String email,String phNo);
	List<CustomerRegistration> findAllByUserEmailOrUserMobileOrUserState(String email,String phNo,String userState);
	CustomerRegistration findByUserEmail(String email);
	CustomerRegistration findByUserid(String userid);
	
	@Query("SELECT cr FROM CustomerRegistration cr WHERE cr.regDate BETWEEN :startDate AND :endDate")
	List<CustomerRegistration> findByRegDateBetween(String startDate, String endDate);
}
