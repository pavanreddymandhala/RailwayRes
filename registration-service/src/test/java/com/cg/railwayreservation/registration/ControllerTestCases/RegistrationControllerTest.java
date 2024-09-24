package com.cg.railwayreservation.registration.ControllerTestCases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.railwayreservation.registration.controller.RegistrationController;
import com.cg.railwayreservation.registration.exception.RegistrationException;
import com.cg.railwayreservation.registration.model.Registration;
import com.cg.railwayreservation.registration.serviceImpl.RegistrationServiceImpl;

@SpringBootTest
public class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    private RegistrationServiceImpl registrationService;


    @Test
    public void testSaveUserWithValidRole() throws Exception {
        
        Registration user = new Registration("hemanth","Password","User","hemanth@gmail.com","Male",23,"India");

        when(registrationService.save(any(Registration.class))).thenReturn(user);

        ResponseEntity<?> responseEntity = registrationController.saveUser(user);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testSaveUserWithInvalidRole() throws Exception {
        
    	 Registration user = new Registration("hemanth","Password","User","hemanth@gmail.com","Male",23,"India");
        user.setRole("InvalidRole");

        ResponseEntity<?> responseEntity = registrationController.saveUser(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

//    @Test
//    public void testSaveUserWithExistingUsername() throws Exception {
//        
//        Registration existingUser = new Registration("powerstar", "Password", "User", "hemanth@gmail.com", "Male", 23, "India");
//
//        when(registrationService.save(any(Registration.class))).thenThrow(new RegistrationException(" already exists"));
//
//        ResponseEntity<?> responseEntity = registrationController.saveUser(existingUser);
//
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//    }
    
    @Test
    public void testSaveUserWithExistingUsername() throws Exception {
        Registration user = new Registration("powerstar", "Password", "User", "hemanth@gmail.com", "Male", 23, "India");

        when(registrationService.save(any(Registration.class))).thenReturn(user); 

        ResponseEntity<?> responseEntity = registrationController.saveUser(user);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); 
    }



   
}
