package com.cg.railwayreservation.help;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.railwayreservation.help.controller.HelpController;
import com.cg.railwayreservation.help.exception.HelpException;
import com.cg.railwayreservation.help.model.HelpModel;
import com.cg.railwayreservation.help.serviceImpl.HelpServiceImpl;
import com.cg.railwayreservation.help.vo.LoginModel;
import com.cg.railwayreservation.help.vo.UserIssueVO;

@SpringBootTest
public class HelpControllerTest {

    @Mock
    private HelpServiceImpl helpService;

    @InjectMocks
    private HelpController helpController;

    
    @Test
    public void testAddIssue() {
        HelpModel helpModel = new HelpModel();
        when(helpService.addissue(any())).thenReturn("Apologies for hearing the Issue. Our Admin will Look into this");

        ResponseEntity<String> response = helpController.addissue(helpModel);

        assertEquals("Apologies for hearing the Issue. Our Admin will Look into this", response.getBody());
       
    }

    @Test
    public void testAddIssueWithEmptyDescription() {
        HelpModel helpModel = new HelpModel(); // No description provided

        when(helpService.addissue(any())).thenThrow(new HelpException("Please provide the Issue."));

        HelpException exception = assertThrows(HelpException.class, () -> helpController.addissue(helpModel));
        assertEquals("Please provide the Issue.", exception.getMessage());
        verify(helpService, times(1)).addissue(any());
    }
    
    @Test
    public void testGetAllIssues_Success() {

        // Create a list of HelpModel objects for testing

        List<HelpModel> helpList = new ArrayList<>();

        HelpModel issue1 = new HelpModel();

        issue1.setUsername("user1");

        issue1.setIssue("Issue 1");

        issue1.setStatus("Resolved");

        issue1.setSolution("Solution 1");

        HelpModel issue2 = new HelpModel();

        issue2.setUsername("user2");

        issue2.setIssue("Issue 2");

        issue2.setStatus("Processing");

        issue2.setSolution("Solution 2");
        helpList.add(issue1);

        helpList.add(issue2);

        when(helpService.getAllIssues()).thenReturn(helpList);

        ResponseEntity<List<HelpModel>> responseEntity = helpController.getAllIssues();
        // Assert that the response is not null

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Assert that the response body contains the list of HelpModel objects

        List<HelpModel> responseBody = responseEntity.getBody();

        assertNotNull(responseBody);

        assertEquals(2, responseBody.size());
    }

    @Test
    public void testGetAllIssuesEmpty() {
        when(helpService.getAllIssues()).thenThrow(new HelpException("Data is not Found"));

        HelpException exception = assertThrows(HelpException.class, () -> helpController.getAllIssues());
        assertEquals("Data is not Found", exception.getMessage());
        verify(helpService, times(1)).getAllIssues();
    }


      @Test
     public void testGetByUsername() {

          String username = "user1";
          LoginModel loginModel = new LoginModel();

          loginModel.setUsername(username);
          List<HelpModel> helpModels = new ArrayList<>();
          UserIssueVO userIssueVO = new UserIssueVO();

          userIssueVO.setLoginModel(loginModel);

          userIssueVO.setHelpModel(helpModels);        
          when(helpService.getByUsername(username)).thenReturn(userIssueVO);
          ResponseEntity<UserIssueVO> responseEntity = helpController.getByUsername(username);

          assertNotNull(responseEntity);
          assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

          }

    @Test
    public void testUpdateIssue() {
        String username = "testUser";
        HelpModel helpModel = new HelpModel();
     
        helpModel.setStatus("processing"); // Valid status

        when(helpService.updateIssue(any(), eq(username))).thenReturn(helpModel);

        ResponseEntity<HelpModel> response = helpController.updateIssue(helpModel, username);

        assertNotNull(response);
       
        verify(helpService, times(1)).updateIssue(any(), eq(username));
    }

    @Test
    public void testUpdateIssueWithInvalidStatus() {
        String username = "testUser";
        HelpModel helpModel = new HelpModel();
       
        helpModel.setStatus("invalidStatus"); // Invalid status

        when(helpService.updateIssue(any(), eq(username))).thenThrow(new HelpException("Give Proper Status"));

        HelpException exception = assertThrows(HelpException.class, () -> helpController.updateIssue(helpModel, username));
        assertEquals("Give Proper Status", exception.getMessage());
        verify(helpService, times(1)).updateIssue(any(), eq(username));
    }

    @Test
    public void testUpdateIssueNoIssueFound() {
        String username = "nonExistingUser";
        HelpModel helpModel = new HelpModel();
        
        helpModel.setStatus("processing"); // Valid status

        when(helpService.updateIssue(any(), eq(username))).thenThrow(new HelpException("There is nothing to update :)"));

        HelpException exception = assertThrows(HelpException.class, () -> helpController.updateIssue(helpModel, username));
        assertEquals("There is nothing to update :)", exception.getMessage());
        verify(helpService, times(1)).updateIssue(any(), eq(username));
    }
}
