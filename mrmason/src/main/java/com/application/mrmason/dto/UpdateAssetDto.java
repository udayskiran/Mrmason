package com.application.mrmason.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAssetDto {
	private String userId;
	private String assetId;
	private String location;
	private String assetBrand;
	private String assetModel;
	private String assetCat;
	private String assetSubCat;
}
