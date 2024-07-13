package com.application.mrmason.entity;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin_asset_details")
public class AdminAsset {

	@Id
	@Column(name = "admin_asset_id")
	private String assetId;

	@Column(name = "asset_category")
	private String assetCat;
	@Column(name = "asset_subcategory")
	private String assetSubCat;
	@Column(name = "asset_type_model")
	private String assetModel;
	@Column(name = "added_by")
	private String addedBy;
	@CreationTimestamp
	@Column(name = "added_date")
	private String addedDate;
	@Column(name = "asset_brand")
	private String assetBrand;

	@PrePersist
	@PreUpdate
	private void prePersist() {
		this.assetId = assetBrand + "_" + assetSubCat;
	}
}
