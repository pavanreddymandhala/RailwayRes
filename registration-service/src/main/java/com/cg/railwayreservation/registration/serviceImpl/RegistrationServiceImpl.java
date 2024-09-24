package com.cg.railwayreservation.registration.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.railwayreservation.registration.Repository.RegistrationRepository;
import com.cg.railwayreservation.registration.exception.RegistrationException;
import com.cg.railwayreservation.registration.model.Registration;


@Service
public class RegistrationServiceImpl {
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	
	@Autowired
	private RegistrationRepository registrationRepository;

	public Registration save(Registration user) {
		// TODO Auto-generated method stub
		Registration existingUser = registrationRepository.findByUsername(user.getUsername());

		if (existingUser != null) {
			// User with the same username already exists, throw an exception
			throw new RegistrationException("Username already exists: " + user.getUsername());
		}
		Registration login = new Registration();
		login.setUsername(user.getUsername());
		login.setPassword(bcryptEncoder.encode(user.getPassword()));
//		login.setPassword(user.getPassword());
		login.setRole(user.getRole());
		login.setGender(user.getGender());
		login.setEmail(user.getEmail());
		login.setCountry(user.getCountry());
		login.setAge(user.getAge());
		return registrationRepository.save(login);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
