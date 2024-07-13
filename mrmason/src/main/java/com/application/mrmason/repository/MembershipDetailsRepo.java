package com.application.mrmason.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.application.mrmason.entity.MembershipDetails;

@Repository
public interface MembershipDetailsRepo extends JpaRepository<MembershipDetails, Long>{
	List<MembershipDetails>   findByUserIdOrderByIdDesc(String userId);
	List<MembershipDetails> findByUserIdAndAssetIdOrderByIdDesc(String userid,String assetid);
	MembershipDetails findByUserId(String userId);
	MembershipDetails findByAssetId(String assetId);
	MembershipDetails findByUserIdAndAssetId(String userId,String assetId);
}
