package com.application.mrmason.service;

import java.util.List;
import com.application.mrmason.dto.UpdateAssetDto;
import com.application.mrmason.entity.AdminAsset;


public interface AdminAssetService {
	AdminAsset addAdminAssets(AdminAsset asset);
	List<AdminAsset> getAssets(String assetId,String assetCat,String assetSubCat,String assetModel,String assetBrand);
	AdminAsset updateAssets(UpdateAssetDto asset);
}
