package com.application.mrmason.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="admin_amc_rates")
public class AdminAmcRate {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "amc_id")
	private String amcId;
	@Column(name = "location")
	private String location;
	@Column(name = "asset_subcategory")
	private String assetSubCat;
	@Column(name = "asset_model")
	private String assetModel;
	@Column(name = "added_by")
	private String addedBy;
	@Column(name = "added_date")
	private String addedDate;
	@Column(name = "asset_brand")
	private String assetBrand;
	@Column(name = "amount")
	private String amount;
	@Column(name = "plan_id")
	private String planId;
	@Column(name = "no_of_days")
	private String noOfDays;
	@Column(name = "status")
	@Builder.Default
	private String status="active";

	@PrePersist
	@PreUpdate
	private void prePersist() {
		this.amcId = assetSubCat + "_" + planId;
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
		this.addedDate = now.format(formatter);
	}
}