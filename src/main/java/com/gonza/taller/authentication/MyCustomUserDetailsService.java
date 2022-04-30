package com.gonza.taller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gonza.taller.model.auth.UserMine;
import com.gonza.taller.repository.UserRepositoryI;



@Service
public class MyCustomUserDetailsService implements UserDetailsService {
	
	
	
	private UserRepositoryI ur;
	
	@Autowired
	public MyCustomUserDetailsService(UserRepositoryI ur) {
		this.ur = ur;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("Username: "+username);
		
		UserMine userApp = ur.findByUsername(username);
		
		
		if (userApp != null) {
			System.out.println("Tipo: "+userApp.getType());
			User.UserBuilder builder = User.withUsername(username).password(userApp.getPassword()).roles(userApp.getType());
			return builder.build();
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}
}