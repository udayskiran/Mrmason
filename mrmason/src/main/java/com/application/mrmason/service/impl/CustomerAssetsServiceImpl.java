package com.application.mrmason.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mrmason.dto.CustomerAssetDto;
import com.application.mrmason.dto.UpdateAssetDto;
import com.application.mrmason.entity.CustomerAssets;
import com.application.mrmason.repository.CustomerAssetsRepo;
import com.application.mrmason.repository.CustomerRegistrationRepo;
import com.application.mrmason.service.CustomerAssetsService;

@Service
public class CustomerAssetsServiceImpl implements CustomerAssetsService {
	@Autowired
	CustomerAssetsRepo assetRepo;
	@Autowired
	CustomerRegistrationRepo regiRepo;

	@Override
	public CustomerAssets saveAssets(CustomerAssets asset) {
		if(regiRepo.findByUserid(asset.getUserId())!=null) {
			return assetRepo.save(asset);
		}
		return null;

	}

	@Override
	public CustomerAssets updateAssets(CustomerAssetDto asset) {
		Optional<CustomerAssets> assetDb = assetRepo.findByUserIdAndAssetId(asset.getUserId(), asset.getAssetId());
		if (assetDb.isPresent()) {
			CustomerAssets user = assetDb.get();
			user.setAssetCat(asset.getAssetCat());
			user.setAssetSubCat(asset.getAssetSubCat());
			user.setDistrict(asset.getDistrict());
			user.setDoorNo(asset.getDoorNo());
			user.setLocation(asset.getLocation());
			user.setPinCode(asset.getPinCode());
			user.setState(asset.getState());
			user.setStreet(asset.getStreet());
			user.setTown(asset.getTown());
			user.setAssetBrand(asset.getAssetBrand());
			user.setAssetModel(asset.getAssetModel());
			return assetRepo.save(user);

		}
		return null;
	}





@Override
	public List<CustomerAssets> getAssets(String userId,String assetId,String location,String assetCat,String assetSubCat,String assetModel,String assetBrand) {

		if(userId!=null && assetId==null && location==null && assetCat==null && assetSubCat==null && assetModel==null && assetBrand==null) {
			Optional<List<CustomerAssets>> user=Optional.of((assetRepo.findByUserIdOrderByIdDesc(userId)));
			return user.get();
		}else if(userId==null && assetId!=null && location==null && assetCat==null && assetSubCat==null && assetModel==null && assetBrand==null) {
			Optional<List<CustomerAssets>> user=Optional.of((assetRepo.findByAssetIdOrderByIdDesc(assetId)));
			return user.get();
		}else if(userId==null && assetId==null && location!=null && assetCat==null && assetSubCat==null && assetModel==null && assetBrand==null) {
			Optional<List<CustomerAssets>> user=Optional.of((assetRepo.findByLocationOrderByIdDesc(location)));
			return user.get();
		}else if(userId==null && assetId==null && location==null && assetCat!=null && assetSubCat==null && assetModel==null && assetBrand==null) {
			Optional<List<CustomerAssets>> user=Optional.of((assetRepo.findByAssetCatOrderByIdDesc(assetCat)));
			return user.get();
		}else if(userId==null && assetId==null && location==null && assetCat==null && assetSubCat!=null && assetModel==null && assetBrand==null) {
			Optional<List<CustomerAssets>> user=Optional.of((assetRepo.findByAssetSubCatOrderByIdDesc(assetSubCat)));
			return user.get();
		}else if(userId==null && assetId==null && location==null && assetCat==null && assetSubCat==null && assetModel!=null && assetBrand==null) {
			Optional<List<CustomerAssets>> user=Optional.of((assetRepo.findByAssetModelOrderByIdDesc(assetModel)));
			return user.get();
		}else if(userId==null && assetId==null && location==null && assetCat==null && assetSubCat==null && assetModel==null && assetBrand!=null) {
			Optional<List<CustomerAssets>> user=Optional.of((assetRepo.findByAssetBrandOrderByIdDesc(assetBrand)));
			return user.get();
		}
		return null;

	}

	@Override
	public CustomerAssetDto getAssetByAssetId(String assetId) {
		if (assetRepo.findAllByAssetId(assetId) != null) {
			Optional<CustomerAssets> assetDb = assetRepo.findAllByAssetId(assetId);
			CustomerAssets assetData = assetDb.get();
			CustomerAssetDto assetDto = new CustomerAssetDto();

			assetDto.setAssetCat(assetData.getAssetCat());
			assetDto.setAssetSubCat(assetData.getAssetSubCat());
			assetDto.setDistrict(assetData.getDistrict());
			assetDto.setDoorNo(assetData.getDoorNo());
			assetDto.setLocation(assetData.getLocation());
			assetDto.setPinCode(assetData.getPinCode());
			assetDto.setState(assetData.getState());
			assetDto.setStreet(assetData.getStreet());
			assetDto.setTown(assetData.getTown());
			assetDto.setAssetModel(assetData.getAssetModel());
			assetDto.setRegDate(assetData.getRegDateFormatted());
			assetDto.setPlanId(assetData.getPlanId());
			assetDto.setMembershipExp(assetData.getMembershipExpDb());
			assetDto.setAssetId(assetData.getAssetId());
			assetDto.setUserId(assetData.getUserId());
			assetDto.setAssetBrand(assetData.getAssetBrand());
			return assetDto;

		}
		return null;
	}

}
