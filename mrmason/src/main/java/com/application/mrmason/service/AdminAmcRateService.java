package com.application.mrmason.service;

import java.util.List;
import com.application.mrmason.entity.AdminAmcRate;

public interface AdminAmcRateService {
	AdminAmcRate addAdminamc(AdminAmcRate amc);
	List<AdminAmcRate> getAmcRates(String amcId,String planId,String assetSubCat,String assetModel,String assetBrand);
	AdminAmcRate updateAmcRates(AdminAmcRate amc);
}

