package com.application.mrmason.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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
@Table(name = "user_services")
@Builder
public class SpServiceDetails {
	@Id
	private String userServicesId;
	private String serviceType;
	private String userId;
	private String qualification;
	private String experience;
	private String charge;
	private String certificate1;
	private String certificate2;
	@Builder.Default
	private String status="active";
	private String availableWithinRange;
	private String pincode;
	private String city;
	
	@PrePersist
	private void prePersist() {
	
        this.userServicesId =serviceType+"_"+userId;
	}
}
