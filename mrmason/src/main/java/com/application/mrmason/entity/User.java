package com.application.mrmason.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="users")
public class User implements UserDetails {

//	private static final long serialVersionUID = 5342327L;
	
	@Id
	@Column(name = "BOD_SEQ_NO")
	public String bodSeqNo;

	@Column(name = "NAME")
	public String name;

	@Column(name = "BUSINESS_NAME")
	public String businessName;

	@Column(name = "MOBILE_NO")
	public String mobile;

	@Column(name = "EMAIL_ID")
	public String email;

	@Column(name = "PASSWORD")
	public String password;

	@Column(name = "ADDRESS")
	public String address;

	@Column(name = "CITY")
	public String city;

	@Column(name = "DISTRICT")
	public String district;

	@Column(name = "STATE")
	public String state;

	@Column(name = "PINCODE_NO")
	public String pincodeNo;


	@Transient
	public LocalDateTime update;
	
	@Column(name = "UPDATE_DATETIME")
	public String updatedDate;

	@CreationTimestamp
	@Column(name = "REGISTRATION_DATETIME")
	public String registeredDate;

	@Column(name = "VERIFIED")
	public String verified ="no";

	@Column(name = "SERVICE_CATEGORY")
	public String serviceCategory;

	@Column(name = "USER_TYPE")
	@Enumerated(EnumType.STRING)
	private UserType userType ;

	@Column(name = "STATUS")
	private String status = "inactive";

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
		this.bodSeqNo = "SP" + year + month + day + hour + minute + second+millis;
		
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
		this.update = now;
		this.updatedDate= now.format(formatter);

	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + getUserType().name()));

	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
