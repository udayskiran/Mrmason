package com.application.mrmason.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
@Table(name="admin_service_name_id")
public class AdminServiceName {
	@Id
	@Column(name="service_id")
	private String serviceId;
	@Column(name="service_name")
	private String serviceName;
	@Column(name="added_by")
	private String addedBy;
	@Column(name="added_date")
	private String addedDate;
	@Column(name="service_subcategory")
	private String serviceSubCategory;

	@PrePersist
	@PreUpdate
	private void prePersist() {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
		this.addedDate= now.format(formatter);

	}
}
