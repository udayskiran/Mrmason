package com.application.mrmason.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.CustomerLogin;
@Repository
public interface CustomerLoginRepo extends JpaRepository<CustomerLogin, Long>{
	CustomerLogin findByUserEmailOrUserMobile(String email,String phno);
	CustomerLogin findByUserEmail(String email);
	CustomerLogin findByUserMobile(String mobile);
	CustomerLogin findByUserEmailIgnoreCaseAndUserPassword(String userEmail, String userPassword);
}
