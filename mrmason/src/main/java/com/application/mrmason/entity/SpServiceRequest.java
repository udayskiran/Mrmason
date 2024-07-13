package com.application.mrmason.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
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
@Builder
@Table(name="serviceperson_servicerequest")
public class SpServiceRequest {
	@Id
	@Column(name="servicerequest_id")
	private String serviceReqId;
	@Column(name="service_person_id")
	private String servicePersonId;
	@Column(name="assignid")
	private String assignId;
	@Column(name="status")
	@Builder.Default
	private String status="assign";
	@Column(name="comment")
	private String comment;
	@Column(name="comment_updated_by")
	private String commentUpdatedBy;
	@Column(name="user_feedback")
	private String userFeedback;
	@Column(name="assigned_date")
	private String assignedDate;
	@Column(name="assigned_by")
	private String assignedBy;
	


	@PrePersist
	private void prePersist() {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
		this.assignedDate= now.format(formatter);
		
		String year = String.valueOf(now.getYear());
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());
        String hour = String.format("%02d", now.getHour());
        String minute = String.format("%02d", now.getMinute());
        String second = String.format("%02d", now.getSecond());
        this.assignId ="AS"+ year + month + day + hour + minute + second;
	}
}

