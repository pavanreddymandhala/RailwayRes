package com.cg.railwayreservation.registration.ServiceTestCases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cg.railwayreservation.registration.Repository.RegistrationRepository;
import com.cg.railwayreservation.registration.exception.RegistrationException;
import com.cg.railwayreservation.registration.model.Registration;
import com.cg.railwayreservation.registration.serviceImpl.RegistrationServiceImpl;
@SpringBootTest
public class RegistrationServiceImplTest {

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private PasswordEncoder bcryptEncoder;

    @Test
    public void testSaveUser() {
       
        Registration newUser = new Registration();
        newUser.setUsername("hemanth");
        newUser.setPassword("Password");
        newUser.setRole("Admin");
        newUser.setEmail("hemanth@gmail.com");
        newUser.setGender("Male");
        newUser.setAge(22);
        newUser.setCountry("India");

     
        when(registrationRepository.save(any(Registration.class))).thenReturn(newUser);


        Registration savedUser = registrationService.save(newUser);

        assertEquals(newUser.getUsername(), savedUser.getUsername());
        assertEquals(newUser.getRole(), savedUser.getRole());
        assertEquals(newUser.getGender(), savedUser.getGender());
        assertEquals(newUser.getEmail(), savedUser.getEmail());
        assertEquals(newUser.getCountry(), savedUser.getCountry());
        assertEquals(newUser.getAge(), savedUser.getAge());
    }

    @Test
    public void testSaveUserWithExistingUsername() {
        // Mock data
        Registration existingUser = new Registration();
        existingUser.setUsername("existingUser");
        existingUser.setPassword("existingPassword");
        existingUser.setRole("User");

        // Mock repository behavior
        when(registrationRepository.findByUsername(existingUser.getUsername())).thenReturn(existingUser);

        // Test the service method
        try {
            registrationService.save(existingUser);
        } catch (RegistrationException e) {
            assertEquals("Username already exists: " + existingUser.getUsername(), e.getMessage());
        }
    }
}
