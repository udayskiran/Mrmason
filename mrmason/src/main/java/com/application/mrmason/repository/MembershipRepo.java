package com.application.mrmason.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.application.mrmason.entity.Membership;

@Repository
public interface MembershipRepo extends JpaRepository<Membership, Long>{
	Membership findByUserId(String userId);
	Membership findByUserEmail(String userEmail);
}
