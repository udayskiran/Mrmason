package com.application.mrmason.entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "service_request_details")
public class ServiceRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REQ_SEQ_ID")
	private long reqSeqId;

	@Column(name = "service_sub_category")
	private String serviceSubCategory;

	@Column(name = "REQUEST_ID")
	private String requestId;

	@CreationTimestamp
	@Column(name = "SERVICE_REQUEST_DATE")
	private String serviceRequestDate;

	@Column(name = "REQUESTED_BY")
	private String requestedBy;

	@Column(name = "REQ_PINCODE")
	private String location;

	@Column(name = "DESCRIPTION") // Corrected the column name
	private String description;

	@Builder.Default
	@Column(name = "STATUS")
	private String status = "NEW";

//    @Transient
//    private LocalDate serviceDate;

	@Column(name = "SERVICE_DATE")
	private String serviceDateDb;

	@Column(name = "ASSETID")
	private String assetId;

	@PrePersist
	private void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		String year = String.valueOf(now.getYear());
		String month = String.format("%02d", now.getMonthValue());
		String day = String.format("%02d", now.getDayOfMonth());
		String hour = String.format("%02d", now.getHour());
		String minute = String.format("%02d", now.getMinute());
		String second = String.format("%02d", now.getSecond());
		this.requestId = year + month + day + hour + minute + second;

		DateTimeFormatter formatterExp = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		this.serviceDateDb = now.format(formatterExp);

	}
}
