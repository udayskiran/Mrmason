package com.application.mrmason.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.CustomerAssets;
@Repository
public interface CustomerAssetsRepo extends JpaRepository<CustomerAssets, Long>{
	Optional<CustomerAssets>  findByUserIdAndAssetId(String userid,String assetId);
	List<CustomerAssets> findByLocationOrderByIdDesc(String location);
	List<CustomerAssets>  findByUserIdOrderByIdDesc(String userid);
	List<CustomerAssets> findByAssetIdOrderByIdDesc(String assetid);
	List<CustomerAssets>  findByAssetModelOrderByIdDesc(String assetModel);
	List<CustomerAssets> findByAssetCatOrderByIdDesc(String assetCat);
	List<CustomerAssets> findByAssetSubCatOrderByIdDesc(String assetSubCat);
	List<CustomerAssets> findByAssetBrandOrderByIdDesc(String assetBrand);
	
	Optional<CustomerAssets> findAllByAssetId(String asssetid);
}
