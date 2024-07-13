package com.application.mrmason.entity;

import jakarta.persistence.Column;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_otp_details")
@Builder
public class ServicePersonLogin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OTP_SEQ_NO")
	public long otpSeqNo;
	@Column(name = "USER_MOBILE")
	public String mobile;
	@Column(name = "OTP")
	public String mOtp;
	@Column(name = "MOB_VERIFY")
	@Builder.Default
	public String mobVerify ="no";
	@Column(name = "E_OTP")
	public String eOtp;
	@Column(name = "USER_EMAIL")
	public String email;
	@Column(name = "E_VERIFY")
	public String eVerify;
	
	
}
