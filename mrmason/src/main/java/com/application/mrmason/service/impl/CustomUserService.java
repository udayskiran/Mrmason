package com.application.mrmason.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.application.mrmason.entity.AdminDetails;
import com.application.mrmason.entity.CustomerRegistration;
import com.application.mrmason.entity.User;
import com.application.mrmason.repository.AdminDetailsRepo;
import com.application.mrmason.repository.CustomerRegistrationRepo;
import com.application.mrmason.repository.UserDAO;



@Service
public class CustomUserService implements UserDetailsService {

	@Autowired
	private CustomerRegistrationRepo customerRegistrationRepo;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private AdminDetailsRepo adminDetailsRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		CustomerRegistration customerOptional = customerRegistrationRepo.findByUserEmailOrUserMobile(username,
				username);
		if (customerOptional != null) {
			return customerOptional;
		}

		AdminDetails adminOptional = adminDetailsRepo.findByEmailOrMobile(username, username);
		if (adminOptional != null) {
			return adminOptional;
		}

		User userRef = userDAO.findByEmailOrMobile(username, username);
		if (userRef != null) {
			return userRef;
		}

		throw new UsernameNotFoundException("User not found with username: " + username);
	}

//	private UserDetails buildUserDetails(CustomerRegistration customer) {
//		return new org.springframework.security.core.userdetails.User(customer.getUserEmail(),
//				customer.getUserPassword(), true, true, true, true, getAuthorities("ROLE_EC"));
//	}
//
//	private UserDetails buildUserDetails(AdminDetails admin) {
//		return new org.springframework.security.core.userdetails.User(admin.getEmail(), admin.getPassword(), true, true,
//				true, true, getAuthorities("ROLE_Adm"));
//	}
//
//	private UserDetails buildUserDetails(com.application.mrmason.entity.User userRefer) {
//		return new org.springframework.security.core.userdetails.User(userRefer.getEmail(), userRefer.getPassword(),
//				true, true, true, true, getAuthorities("ROLE_Developer"));
//	}
//
//	private Collection<? extends GrantedAuthority> getAuthorities(String userType) {
//		return Collections.singleton(new SimpleGrantedAuthority(userType));
//	}

}
