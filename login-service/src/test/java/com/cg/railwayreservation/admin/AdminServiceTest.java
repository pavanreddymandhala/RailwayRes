package com.cg.railwayreservation.admin;
import com.cg.railwayreservation.admin.exception.UserNotFoundException;
import com.cg.railwayreservation.admin.model.LoginModel;

import com.cg.railwayreservation.admin.repository.LoginRepository;
import com.cg.railwayreservation.admin.service.ServiceImpl;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

 

import java.util.ArrayList;

import java.util.List;

public class AdminServiceTest {
	@InjectMocks
    private ServiceImpl service;

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {

        List<LoginModel> loginModels = new ArrayList<>();
        when(loginRepository.findAll()).thenReturn(loginModels);
        List<LoginModel> result = service.getAllUsers();
        assertNotNull(result);
    }

    @Test
    public void testGetByUsername_UserFound() {

        String username = "testUser";

        LoginModel sampleLogin = new LoginModel();
        when(loginRepository.findByUsername(username)).thenReturn(sampleLogin);
        LoginModel result = service.getByUsername(username);
        assertNotNull(result);

    }

    @Test
    public void testGetByUsername_UserNotFound() {

        String username = "nonExistentUser";
        when(loginRepository.findByUsername(username)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> {
            service.getByUsername(username);

        });
    }

    @Test
    public void testUpdateByUsername_UserFound() {

        String username = "testUser";

        LoginModel sampleLogin = new LoginModel();

        String newPassword = "newPassword";

        when(loginRepository.findByUsername(username)).thenReturn(sampleLogin);

        when(passwordEncoder.encode(newPassword)).thenReturn("encodedPassword");

        when(loginRepository.save(sampleLogin)).thenReturn(sampleLogin);

        LoginModel updatedLogin = service.updateByUsername(username, sampleLogin);

        assertNotNull(updatedLogin);

    }

 

    @Test
    public void testUpdateByUsername_UserNotFound() {
        String username = "nonExistentUser";
        LoginModel sampleLogin = new LoginModel();
        when(loginRepository.findByUsername(username)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> {
            service.updateByUsername(username, sampleLogin);
        });

    }

}