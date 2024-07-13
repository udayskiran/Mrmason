package com.application.mrmason.entity;


import java.time.LocalDateTime;
import java.util.Random;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="c_user_membership_details")
public class MembershipDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auto_id")
	private long id;
	@Column(name="plan_id")
	private String planId;
	@Column(name="purchase_id")
	private String purchaseId;
	@Column(name="amount")
	private String amount;
	@Column(name="user_id")
	private String userId;
	@Column(name="asset_id")
	private String assetId;
	@Column(name="expirty_date")
	private String expDate;

	
	@PrePersist
	public void prePresist() {
		if(purchaseId==null) {
			LocalDateTime now = LocalDateTime.now();
			String year = String.valueOf(now.getYear());
			String month = String.format("%02d", now.getMonthValue());
			String day = String.format("%02d", now.getDayOfMonth());
			String hour = String.format("%02d", now.getHour());
			String minute = String.format("%02d", now.getMinute());
			String second = String.format("%02d", now.getSecond());
			String millis = String.format("%03d", now.getNano() / 1000000).substring(0, 2);
		    String randomDigits = String.format("%04d", new Random().nextInt(10000)); // Random 4-digit number
		    this.purchaseId= "ORD" + year + month + day + hour + minute + second + millis + randomDigits;
		}
		
	}
}
