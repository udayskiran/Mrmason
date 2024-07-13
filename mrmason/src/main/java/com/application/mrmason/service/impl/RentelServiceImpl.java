package com.application.mrmason.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.application.mrmason.entity.CustomerAssets;
import com.application.mrmason.entity.Rentel;
import com.application.mrmason.repository.CustomerAssetsRepo;
import com.application.mrmason.repository.RentelRepo;
import com.application.mrmason.service.RentelService;

@Service
public class RentelServiceImpl implements RentelService{
	@Autowired
	public RentelRepo rentRepo;
	@Autowired
	public CustomerAssetsRepo assetRepo;
	
	@Override
	public Rentel addRentalReq(Rentel rent) {
		Optional<CustomerAssets> user=assetRepo.findByUserIdAndAssetId(rent.getUserId(),rent.getAssetId());
		if(user.isPresent()) {
			return rentRepo.save(rent);
		}
		return null;
	}
	@Override
	public List<Rentel> getRentalReq(String assetId,String userId) {
		Optional<List<Rentel>> rentUser=Optional.of(rentRepo.findByAssetIdOrUserId(assetId, userId));
		if(rentUser.isPresent()) {
			
			return rentUser.get();
		}
		return null;
	}
	@Override
	public Rentel updateRentalReq(Rentel rent) {
		Optional<Rentel> user=Optional.of(rentRepo.findByAssetIdAndUserId(rent.getAssetId(),rent.getUserId()));
		if(user.isPresent()) {
			Rentel rentUser=user.get();
			rentUser.setAmountper30days(rent.getAmountper30days());
			rentUser.setAmountPerDay(rent.getAmountPerDay());
			rentUser.setAvailableLocation(rent.getAvailableLocation());
			rentUser.setDelivery(rent.getDelivery());
			rentUser.setPickup(rent.getPickup());
			rentUser.setIsAvailRent(rent.getIsAvailRent());
			
			return rentRepo.save(rentUser);
		}
		return null;
	}

}
