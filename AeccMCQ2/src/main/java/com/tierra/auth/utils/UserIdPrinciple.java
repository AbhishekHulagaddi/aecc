package com.tierra.auth.utils;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.tierra.auth.domain.User;
import com.tierra.auth.repo.UserRepo;

@Component
public class UserIdPrinciple {
	
	@Autowired
	UserRepo userRepo;
	
	 public UUID getUserId() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication != null && authentication.isAuthenticated()) {
	            // Get the logged-in user's ID
	        	 UserDetails userDetails = ((UserDetails) authentication.getPrincipal());
	            String userName = userDetails.getUsername();
	            User User = userRepo.findByUserName(userName).get();   		
	            return User.getUserId();
	        }
	        return null;
	    }

}
