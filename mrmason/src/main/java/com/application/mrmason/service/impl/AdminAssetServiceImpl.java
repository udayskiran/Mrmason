package com.application.mrmason.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mrmason.dto.UpdateAssetDto;
import com.application.mrmason.entity.AdminAsset;
import com.application.mrmason.repository.AdminAssetRepo;
import com.application.mrmason.service.AdminAssetService;

@Service
public class AdminAssetServiceImpl implements AdminAssetService {

	@Autowired
	public AdminAssetRepo adminAssetRepo;

	@Override
	public AdminAsset addAdminAssets(AdminAsset asset) {

		return adminAssetRepo.save(asset);
	}

	@Override
	public List<AdminAsset> getAssets(String assetId,String assetCat,String assetSubCat,String assetModel,String assetBrand) {
		
        if( assetId!=null && assetCat==null && assetSubCat==null && assetModel==null && assetBrand==null) {
			Optional<List<AdminAsset>> user=Optional.of((adminAssetRepo.findByAssetIdOrderByAddedDateDesc(assetId)));
			return user.get();
		}else if(assetId==null && assetCat!=null && assetSubCat==null && assetModel==null && assetBrand==null) {
			Optional<List<AdminAsset>> user=Optional.of((adminAssetRepo.findByAssetCatOrderByAddedDateDesc(assetCat)));
			return user.get();
		}else if(assetId==null && assetCat==null && assetSubCat!=null && assetModel==null && assetBrand==null) {
			Optional<List<AdminAsset>> user=Optional.of((adminAssetRepo.findByAssetSubCatOrderByAddedDateDesc(assetSubCat)));
			return user.get();
		}else if( assetId==null && assetCat==null && assetSubCat==null && assetModel!=null && assetBrand==null) {
			Optional<List<AdminAsset>> user=Optional.of((adminAssetRepo.findByAssetModelOrderByAddedDateDesc(assetModel)));
			return user.get();
		}else if(assetId==null && assetCat==null && assetSubCat==null && assetModel==null && assetBrand!=null) {
			Optional<List<AdminAsset>> user=Optional.of((adminAssetRepo.findByAssetBrandOrderByAddedDateDesc(assetBrand)));
			return user.get();
		}
		return null;
	}

	@Override
	public AdminAsset updateAssets(UpdateAssetDto asset) {
		String assetId=asset.getAssetId();
		String assetCat=asset.getAssetCat();
		String assetSubCat=asset.getAssetSubCat();
		String assetModel=asset.getAssetModel();
		String assetBrand=asset.getAssetBrand();
		Optional<AdminAsset>  adminAsset=Optional.of(adminAssetRepo.findByAssetId(assetId));
		if(adminAsset.isPresent()) {
			adminAsset.get().setAssetCat(assetCat);
			adminAsset.get().setAssetSubCat(assetSubCat);
			adminAsset.get().setAssetModel(assetModel);
			adminAsset.get().setAssetBrand(assetBrand);
//			adminAsset.get().setAssetId(assetBrand+"_"+assetSubCat);
			
			return adminAssetRepo.save(adminAsset.get());
		}
		return null;
	}

}
