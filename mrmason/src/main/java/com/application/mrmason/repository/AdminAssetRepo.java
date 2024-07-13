package com.application.mrmason.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.application.mrmason.entity.AdminAsset;
@Repository
public interface AdminAssetRepo extends JpaRepository<AdminAsset, String>{
	
	List<AdminAsset> findByAssetIdOrderByAddedDateDesc(String assetid);
	List<AdminAsset> findByAssetModelOrderByAddedDateDesc(String assetModel);
	List<AdminAsset> findByAssetCatOrderByAddedDateDesc(String assetCat);
	List<AdminAsset> findByAssetSubCatOrderByAddedDateDesc(String assetSubCat);
	List<AdminAsset> findByAssetBrandOrderByAddedDateDesc(String assetBrand);
	AdminAsset findByAssetId(String assetId);
	
}
