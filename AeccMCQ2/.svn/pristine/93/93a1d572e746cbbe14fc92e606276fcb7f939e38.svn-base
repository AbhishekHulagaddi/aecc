package com.rim.auth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.rim.auth.domain.User;
import com.rim.auth.repo.UserRepo;
import com.rim.auth.serviceImpl.UserDetailsImpl;

import jakarta.transaction.Transactional;

@Component
public class UserDetailsServiceImpl implements  UserDetailsService{
	@Autowired
	UserRepo userRepo;

	   @Override
	   @Transactional
	   public UserDetails loadUserByUsername(String userName) 
	           throws UsernameNotFoundException  {
	      
	      User user = userRepo
	            .findByUserName(userName)
	            .orElseThrow(() -> new UsernameNotFoundException
	                  ("user Not Found with username: " 
	            + userName));

	      return UserDetailsImpl.build(user);
	      

	   }

}
