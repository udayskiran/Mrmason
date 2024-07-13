package com.application.mrmason.entity;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name="admin_email")
public class AdminMail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String emailid;
	private String pwd;
	private String updatedBy;
	@UpdateTimestamp
	private String updatedDate;
	private String smtpPort;
	private String mailHost;
	@Builder.Default
	private String smtpAuth="true";
	@Builder.Default
	private String starttlsEnable="true";
	private String emailSubject;
	private String staticLocations;
	private String template;
	
}
