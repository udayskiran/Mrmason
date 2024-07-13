package com.application.mrmason.entity;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name="c_user_membership")
public class Membership {
	@Id
	@Column(name="user_session_id")
	private String userEmail;
	@Column(name="is_amc")
	@Builder.Default
	private String isAmc="yes";
	@Column(name="user_id")
	private String userId;
	@UpdateTimestamp
	@Column(name="membership_renewal_date")
	private String membershipRenewalDate;
	@Column(name="purchase_id")
	private String purchaseId;
	@Column(name="amount")
	private String amount;
	
}
