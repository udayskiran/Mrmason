package com.application.mrmason.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.AdminAmcRate;


@Repository
public interface AdminAmcRateRepo extends JpaRepository<AdminAmcRate,Long>{
	AdminAmcRate findByAmcId(String amcId);
	List<AdminAmcRate> findByAmcIdOrderByAddedDateDesc(String amcId);
	List<AdminAmcRate> findByAssetModelOrderByAddedDateDesc(String assetModel);
	List<AdminAmcRate> findByPlanIdOrderByAddedDateDesc(String planId);
	List<AdminAmcRate> findByAssetSubCatOrderByAddedDateDesc(String assetSubCat);
	List<AdminAmcRate> findByAssetBrandOrderByAddedDateDesc(String assetBrand);
}
