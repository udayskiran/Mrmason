package com.application.mrmason.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.CustomerMobileOtp;
@Repository
public interface CustomerMobileOtpRepo extends JpaRepository<CustomerMobileOtp, Long> {
	CustomerMobileOtp findByMobileNum(String mobile);
}
