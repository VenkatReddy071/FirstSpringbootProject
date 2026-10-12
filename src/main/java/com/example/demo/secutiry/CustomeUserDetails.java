package com.example.demo.secutiry;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.constants.Constants;
import com.example.demo.entities.UsersEntity;

import com.example.demo.repositry.UserRepositry;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomeUserDetails implements UserDetailsService{

	 private final UserRepositry userRepositry;

	 @Override
	 public UserDetails loadUserByUsername(String email)
	         throws UsernameNotFoundException {

	     UsersEntity user = userRepositry.findByEmail(email)
	             .orElseThrow(() -> new UsernameNotFoundException(
	                     Constants.USER_NOT_FOUND));
	     String roleName = user.getUserRole()
	                .getRoleName()
	                .toString();
	     return org.springframework.security.core.userdetails.User
	             .withUsername(user.getEmail())
	             .password(user.getPassword())
	             .authorities(roleName)
	             .build();
	 }
}
