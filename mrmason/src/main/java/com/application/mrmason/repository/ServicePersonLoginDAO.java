package com.application.mrmason.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.ServicePersonLogin;

@Repository
public interface ServicePersonLoginDAO extends JpaRepository<ServicePersonLogin, Long> {

	ServicePersonLogin findByEmail(String email);

	ServicePersonLogin findByEmailOrMobile(String email, String mobile);

	ServicePersonLogin findByMobile(String mobile);

}
