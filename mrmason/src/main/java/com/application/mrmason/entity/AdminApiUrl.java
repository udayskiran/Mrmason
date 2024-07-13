package com.application.mrmason.entity;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminApiUrl {
	@Id
	private String systemId;
	private String ip;
	private String url;
	private String updatedBy;
	@UpdateTimestamp
	private String updatedDate;
}
