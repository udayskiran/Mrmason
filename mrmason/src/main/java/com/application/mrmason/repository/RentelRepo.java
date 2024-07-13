package com.application.mrmason.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.Rentel;

@Repository
public interface RentelRepo extends JpaRepository<Rentel, String>{
	Rentel findByAssetIdAndUserId(String assetId,String userId);
	List<Rentel> findByAssetIdOrUserId(String assetId,String userId);
}
