package com.application.mrmason.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="c_login")
public class CustomerLogin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="email")
	private String userEmail;
	@Column(name="mobile")
	private String userMobile;
	@Column(name="password")
	private String userPassword;
	@Column(name="mobileverified")
	private String mobileVerified;
	@Column(name="emailverified")
	private String emailVerified;
	@Column(name="status")
	private String status;
	

}
