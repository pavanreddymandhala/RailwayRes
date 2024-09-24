package com.cg.railwayreservation.train.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.cg.railwayreservation.train.model.TrainModel;
import com.cg.railwayreservation.train.service.TrainService;

@SpringBootTest
public class TrainServiceControllerTest {

	@Autowired
	private TrainController traincontroller;

	@MockBean
	private TrainService trainservice;
	
	@Test
	public void addTrainModel_test() {
		TrainModel train = new TrainModel("1", "Express Train", "Warangal", "Hyderabad", 80, 50, "10:00 AM");
		when(trainservice.addTrainModel(train)).thenReturn(train);
		ResponseEntity<TrainModel> response = traincontroller.addTrainModel(train);
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(train, response.getBody());
	}
	
	@Test
	public void getAllTrains_test() {
		List<TrainModel> train = new ArrayList<>();
		train.add(new TrainModel("1", "Express Train", "Warangal", "Hyderabad", 80, 50, "10:00 AM"));
		train.add(new TrainModel("2", "Intercity", "Secundrabad", "kazipet", 75, 40, "7:45 AM"));

		when(trainservice.getAllTrains()).thenReturn(train);
		ResponseEntity<List<TrainModel>> response = traincontroller.getAllTrains();
		List<TrainModel> trains = response.getBody();
		assertEquals(2, trains.size());
	}
	
	  @Test
	  public void getTrainById_test(){
		TrainModel t = new TrainModel("1", "Express Train", "Warangal", "Hyderabad", 80, 40, "10:00 AM");
		when(trainservice.getTrainById("1")).thenReturn(t);
		ResponseEntity<TrainModel> response=traincontroller.getTrainById("1");
		TrainModel train = response.getBody();
		assertEquals(t, train);
	}
	  

//	  @Test
//		public void getTrainByName_test() {
//			List<TrainModel> train = new ArrayList<>();
//
//			train.add(new TrainModel("1", "Express Train", "Nandyal", "Banglore", 67, 67, "10:00 AM"));
//			train.add(new TrainModel("2", "Kachiguda", "Banglore", "Nandyak", 67, 67, "10:00 AM"));
//
//			when(trainservice.getTrainsbyname("Kachiguda")).thenReturn(train);
//			ResponseEntity<List<TrainModel>> response = traincontroller.getTrainsbyname("Kachiguda");
//			List<TrainModel> trains = response.getBody();
//			assertEquals(2, trains.size());
//		}
	 
	  @Test
	  public void getTrainByName_test()  {
	      
	      List<TrainModel> trainList = new ArrayList<>();
	      trainList.add(new TrainModel("1", "Intercity", "Warangal", "Hyderabad", 80, 30, "7:45 AM"));
	        trainList.add(new TrainModel("2", "shatavana", "warangal", "Secundrabad", 105, 90, "12:30 PM"));

	      List<TrainModel> filteredList = new ArrayList<>();
	      for (TrainModel train : trainList) {
	          if ("Intercity".equals(train.getTrainName())) {
	              filteredList.add(train);
	          }
	      }

	      when(trainservice.getTrainsbyname("Intercity")).thenReturn(filteredList);

	      ResponseEntity<List<TrainModel>> response = traincontroller.getTrainsbyname("Intercity");

	      assertNotNull(response);
	      List<TrainModel> trains = response.getBody();
	      assertEquals(1, trains.size());
	      assertEquals("Intercity", trains.get(0).getTrainName());
	  }


	  @Test
	    public void deleteTrain_test() {
	        
	        String trainNo = "123";

	        String expectedResponse = "Train with number " + trainNo + " has been deleted successfully";

	        when(trainservice.deleteTrain(trainNo)).thenReturn(expectedResponse);

	        ResponseEntity<String> response = traincontroller.deleteTrain(trainNo);

	        assertNotNull(response);

	        assertEquals(200, response.getStatusCodeValue());

	        assertEquals(expectedResponse, response.getBody());
	    }
	
	  @Test
	    public void findByLocation_test() {
	        
	        String trainFrom = "Warangal";
	        String trainTo = "Hyderabad";

	        List<TrainModel> trainList = new ArrayList<>();
	        trainList.add(new TrainModel("1", "Intercity", "Warangal", "Hyderabad", 80, 30, "7:45 AM"));
	        trainList.add(new TrainModel("2", "shatavana", "warangal", "Secundrabad", 105, 90, "12:30 PM"));

	        List<TrainModel> filteredList = new ArrayList<>();
		      for (TrainModel train : trainList) {
		          if (trainFrom.equals(train.getTrainFrom()) && trainTo.equals(train.getTrainTo())) {
		              filteredList.add(train);
		          }
		      }
	        
	        when(trainservice.findByLocation(trainFrom, trainTo)).thenReturn(filteredList);

	        ResponseEntity<List<TrainModel>> response = traincontroller.findByLocation(trainFrom, trainTo);

	        assertNotNull(response);

	        assertEquals(200, response.getStatusCodeValue());

	        List<TrainModel> trains = response.getBody();
	        assertNotNull(trains);
	        assertEquals(1, trains.size());
	        
	    }
	  
	  @Test
	    public void updateTrain_test() {
	        String trainNo = "123";

	        TrainModel updatedTrain = new TrainModel(trainNo, "UpdatedTrain", "Warangal", "Hyderabad", 80, 30, "7:45 AM");

	        when(trainservice.updateTrain(trainNo, updatedTrain)).thenReturn(updatedTrain);

	        ResponseEntity<TrainModel> response = traincontroller.updateTrain(trainNo, updatedTrain);

	        assertNotNull(response);

	        assertEquals(200, response.getStatusCodeValue());

	        assertEquals(updatedTrain, response.getBody());
	    }

}
	  
