package com.application.mrmason.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="service_person_availability_details")
public class SPAvailability {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SEP_SEQ_NO")
	public int sepSeqNo;
	
	@Column(name="SERVICE_PERSON_ID")
	public String bodSeqNo;
	
	@Transient
	public LocalDateTime updatedDate;
	
	@Column(name="DATETIME_OF_UPDATE")
	public String dateTimeOfUpdate;
	
	@Column(name="AVAILABILITY")
	public String availability;
	
	@Column(name="LOCATION")
	public String address;
	

	@PrePersist
	private void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
		this.updatedDate = now;
		this.dateTimeOfUpdate= now.format(formatter);

	}
}
