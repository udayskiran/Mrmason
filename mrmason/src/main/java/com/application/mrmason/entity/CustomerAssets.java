package com.application.mrmason.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@Table(name = "c_assets")
public class CustomerAssets {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long Id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "asset_id")
	private String assetId;

	@Column(name = "asset_cat")
	private String assetCat;

	@Column(name = "asset_sub_cat")
	private String assetSubCat;

	@Column(name = "location")
	private String location;

	@Column(name = "street")
	private String street;

	@Column(name = "door_no")
	private String doorNo;

	@Column(name = "town")
	private String town;

	@Column(name = "district")
	private String district;

	@Column(name = "state")
	private String state;

	@Column(name = "pin_code")
	private String pinCode;

	@UpdateTimestamp
	@Transient
	private LocalDateTime updateDate;

	@CreationTimestamp
	@Transient
	private LocalDateTime regDate;

	@Builder.Default
	@Column(name = "plan_id")
	private String planId = "plan000";

	@Column(name = "asset_brand")
	private String assetBrand;

	@Column(name = "asset_model")
	private String assetModel;

	@Transient
	private LocalDate membershipExp;

	@Column(name = "register_date")
	private String regDateFormatted; // Formatted registration date

	@Column(name = "update_date")
	private String updateDateFormatted; // Formatted update date

	@Column(name = "membership_expiry_date")
	private String membershipExpDb;

	@PrePersist
	private void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		String year = String.valueOf(now.getYear());
		String month = String.format("%02d", now.getMonthValue());
		String day = String.format("%02d", now.getDayOfMonth());
		String hour = String.format("%02d", now.getHour());
		String minute = String.format("%02d", now.getMinute());
		String second = String.format("%02d", now.getSecond());
		String millis = String.format("%03d", now.getNano() / 1000000).substring(0, 2); // Convert nanoseconds to
																						// milliseconds
//		String amPm = now.getHour() < 12 ? "am" : "pm"; // Determine AM or PM

		// Generate asset ID
		this.assetId = "AA" + year + month + day + hour + minute + second + millis;

		// Format registration date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
		this.regDate = now;
		this.regDateFormatted = now.format(formatter);

		// Format update date
		this.updateDate = now;
		this.updateDateFormatted = now.format(formatter);
		DateTimeFormatter formatterExp = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		// Calculate membership expiration date
		if (this.regDate != null) {
			
			this.membershipExp = this.regDate.toLocalDate().plusYears(1);
			this.membershipExpDb = this.membershipExp.format(formatterExp);
		}
		

	}
}
