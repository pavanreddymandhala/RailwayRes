package com.cg.railwayreservation.help;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.railwayreservation.help.exception.HelpException;
import com.cg.railwayreservation.help.model.HelpModel;
import com.cg.railwayreservation.help.repository.HelpRepository;

@SpringBootTest
public class HelpRepositoryTest {

	@Autowired
	private HelpRepository helpRepository;

	@BeforeEach
	public void setUp() {
	    helpRepository = mock(HelpRepository.class);
	}

	 
	   @Test
	    public void testFindAll() {
	        // Arrange
	        List<HelpModel> helpModels = new ArrayList<>();
	        helpModels.add(new HelpModel("6c12","user1", "Issue 1", "New", ""));
	        helpModels.add(new HelpModel("6c13","user2", "Issue 2", "New", ""));
	        when(helpRepository.findAll()).thenReturn(helpModels);

	        // Act
	        List<HelpModel> result = helpRepository.findAll();

	        // Assert
	        assertEquals(2, result.size());
	        assertEquals("Issue 1", result.get(0).getIssue());
	        assertEquals("Issue 2", result.get(1).getIssue());
	    }

	   
	   @Test
	    public void testFindByUsername() {
	        // Arrange
	        String username = "user1";
	        HelpModel helpModel = new HelpModel("6c12",username, "Issue 1", "New", "");
	        when(helpRepository.findByUsername(username)).thenReturn(helpModel);

	        // Act
	        HelpModel result = helpRepository.findByUsername(username);

	        // Assert
	        assertEquals(username, result.getUsername());
	        assertEquals("Issue 1", result.getIssue());
	    }
	   
	   @Test
	    public void testFindByIssueId() {
	        // Arrange
	        String issueId = "6c12";
	        HelpModel helpModel = new HelpModel(issueId,"username", "Issue 1", "New", "");
	        when(helpRepository.findByIssueId(issueId)).thenReturn(helpModel);

	        // Act
	        HelpModel result = helpRepository.findByIssueId(issueId);

	        // Assert
	        assertEquals(issueId, result.getIssueId());
	        assertEquals("Issue 1", result.getIssue());
	    }
  
//	   @Test
//	    public void testFindByUsername_NotFound() {
//	        // Arrange
//	        String username = "user1";
//	        when(helpRepository.findByUsername(username)).thenReturn(null);
//
//	        // Act & Assert
//	        assertThrows(HelpException.class, () -> helpRepository.findByUsername(username));
//	    }

	   
	   @Test
	    public void testUpdateIssue() {
	        // Arrange
	        String username = "user1";
	        HelpModel helpModel1 = new HelpModel("6c12",username, "Issue 1", "New", "");
	        HelpModel helpModel2 = new HelpModel("6c13",username, "Issue 1", "processing", "Solution");
	        when(helpRepository.findByUsername(username)).thenReturn(helpModel1);
	        when(helpRepository.save(any())).thenReturn(helpModel2);

	        // Act
	        HelpModel result = helpRepository.save(helpModel2);

	        // Assert
	        assertEquals("processing", result.getStatus());
	        assertEquals("Solution", result.getSolution());
	    }
	
}
