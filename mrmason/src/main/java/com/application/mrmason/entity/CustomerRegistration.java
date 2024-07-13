package com.application.mrmason.entity;

import java.time.LocalDateTime;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "c_registration")
public class CustomerRegistration implements UserDetails {

	private static final long serialVersionUID = 5342328L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "userid")
	private String userid;

	@Column(name = "useremail")
	private String userEmail;

	@Column(name = "usermobile")
	private String userMobile;

	@Column(name = "userpassword")
	@Transient
	private String userPassword;

	@Column(name = "usertype")
	@Enumerated(EnumType.STRING)
	private UserType userType;

	@Column(name = "username")
	private String userName;

	@Column(name = "usertown")
	private String userTown;

	@Column(name = "userdistrict")
	private String userDistrict;

	@Column(name = "userstate")
	private String userState;

	@Column(name = "userpincode")
	private String userPincode;

	@CreationTimestamp
	@Column(name = "regdate")
	private String regDate;


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
		this.userid ="CU"+ year + month + day + hour + minute + second+millis;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + getUserType().name()));
	}

	@Override
	public String getPassword() {
		return this.userPassword;
	}

	@Override
	public String getUsername() {
		return this.userEmail;
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
