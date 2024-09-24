package com.cg.railwayreservation.registration.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cg.railwayreservation.registration.model.Registration;

public interface RegistrationRepository extends MongoRepository<Registration,String>{

	Registration findByUsername(String username);

}
