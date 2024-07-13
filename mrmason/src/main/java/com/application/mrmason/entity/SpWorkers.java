package com.application.mrmason.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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
@Table(name="sp_workers")
public class SpWorkers {

	@Id
	@Column(name="worker_id")
	private String workerId;
	@Column(name="service_person_id")
	private String servicePersonId;
	@Column(name="worker_name")
	private String workerName;
	@Column(name="worker_phone_number")
	private String workPhoneNum;
	@Column(name="worker_location")
	private String workerLocation;
	@Column(name="worker_availability")
	private String workerAvail;
	
	@PrePersist
	private void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		String year = String.valueOf(now.getYear());
		String month = String.format("%02d", now.getMonthValue());
		String day = String.format("%02d", now.getDayOfMonth());
		String hour = String.format("%02d", now.getHour());
		String minute = String.format("%02d", now.getMinute());
		String second = String.format("%02d", now.getSecond());
		String millis = String.format("%03d", now.getNano() / 1000000).substring(0, 2); 
		this.workerId ="WO"+ year + month + day + hour + minute + second+millis;
	}
	
}
