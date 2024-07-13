package com.application.mrmason.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.CustomerEmailOtp;

@Repository
public interface CustomerEmailOtpRepo extends JpaRepository<CustomerEmailOtp,Long>{

	CustomerEmailOtp findByEmail(String email);
}
