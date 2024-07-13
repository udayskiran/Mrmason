package com.application.mrmason.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.application.mrmason.entity.AdminAmcRate;
import com.application.mrmason.repository.AdminAmcRateRepo;
import com.application.mrmason.service.AdminAmcRateService;

@Service
public class AdminAmcRateServiceImpl implements AdminAmcRateService{

	@Autowired
	AdminAmcRateRepo amcRepo;

	@Override
	public AdminAmcRate addAdminamc(AdminAmcRate amc) {
		
		return amcRepo.save(amc);
	}

	@Override
	public List<AdminAmcRate> getAmcRates(String amcId,String planId,String assetSubCat,String assetModel,String assetBrand) {
	
        if( amcId!=null && planId==null && assetSubCat==null && assetModel==null && assetBrand==null) {
			Optional<List<AdminAmcRate>> user=Optional.of((amcRepo.findByAmcIdOrderByAddedDateDesc(amcId)));
			return user.get();
		}else if(amcId==null && planId!=null && assetSubCat==null && assetModel==null && assetBrand==null) {
			Optional<List<AdminAmcRate>> user=Optional.of((amcRepo.findByPlanIdOrderByAddedDateDesc(planId)));
			return user.get();
		}else if(amcId==null && planId==null && assetSubCat!=null && assetModel==null && assetBrand==null) {
			Optional<List<AdminAmcRate>> user=Optional.of((amcRepo.findByAssetSubCatOrderByAddedDateDesc(assetSubCat)));
			return user.get();
		}else if( amcId==null && planId==null && assetSubCat==null && assetModel!=null && assetBrand==null) {
			Optional<List<AdminAmcRate>> user=Optional.of((amcRepo.findByAssetModelOrderByAddedDateDesc(assetModel)));
			return user.get();
		}else if(amcId==null && planId==null && assetSubCat==null && assetModel==null && assetBrand!=null) {
			Optional<List<AdminAmcRate>> user=Optional.of((amcRepo.findByAssetBrandOrderByAddedDateDesc(assetBrand)));
			return user.get();
		}
		return null;
	}

	@Override
	public AdminAmcRate updateAmcRates(AdminAmcRate amc) {
		String amcId=amc.getAmcId();
		String amount=amc.getAmount();
		String location=amc.getLocation();
		String assetModel=amc.getAssetModel();
		String assetBrand=amc.getAssetBrand();
		Optional<AdminAmcRate>  adminAmc=Optional.of(amcRepo.findByAmcId(amcId));
		if(adminAmc.isPresent()) {
			adminAmc.get().setAmount(amount);;
			adminAmc.get().setLocation(location);
			adminAmc.get().setAssetModel(assetModel);
			adminAmc.get().setAssetBrand(assetBrand);
			
			return amcRepo.save(adminAmc.get());
		}
		return null;
	}



}