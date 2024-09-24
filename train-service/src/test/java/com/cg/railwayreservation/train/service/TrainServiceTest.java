package com.cg.railwayreservation.train.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.railwayreservation.train.exception.TrainNotFoundException;
import com.cg.railwayreservation.train.model.TrainModel;
import com.cg.railwayreservation.train.repository.TrainRepository;

@SpringBootTest
public class TrainServiceTest {

	@Autowired
	private TrainService trainServiceImpl;

	@MockBean
	private TrainRepository trainRepository;

	@Test
	public void addTrainModel_test() {
		TrainModel train = new TrainModel("1", "Express Train", "Nandyal", "Banglore", 67, 67, "10:00 AM");
		when(trainRepository.save(train)).thenReturn(train);
		System.out.println(trainServiceImpl.addTrainModel(train) + "*****************************");
		System.out.println(train + "Error is there ******************************");
		assertEquals(train, trainServiceImpl.addTrainModel(train));
	}

	@Test
	public void addTrainModel_MissingFields() {
		TrainModel train = new TrainModel("", "Express Train", "Nandyal", "Banglore", 67, 100, "10:00 AM");

		// Call the service method and expect a TrainNotFoundException
		assertThrows(TrainNotFoundException.class, () -> {
			trainServiceImpl.addTrainModel(train);
		});
	}

	@Test
	public void getTrains_DataFound() {
		List<TrainModel> train = new ArrayList<>();

//		TrainModel t = new TrainModel("1","Express Train","Nandyal","Banglore",67,67,"10:00 AM");

		train.add(new TrainModel("1", "Express Train", "Nandyal", "Banglore", 67, 67, "10:00 AM"));
		train.add(new TrainModel("2", "Kachiguda", "Banglore", "Nandyak", 67, 67, "10:00 AM"));

		when(trainRepository.findAll()).thenReturn(train);
		assertEquals(2, trainServiceImpl.getAllTrains().size());
	}

	@Test
	public void getTrainByTrainNo_DataFound() {
		TrainModel t = new TrainModel("1", "Express Train", "Nandyal", "Banglore", 67, 67, "10:00 AM");
		when(trainRepository.findById("1")).thenReturn(Optional.of(t));
		Optional<TrainModel> result = Optional.of(trainServiceImpl.getTrainById("1"));
		assertEquals(t, result.get());
	}

	@Test
	public void getTrainByTrainNo_DataNotFound() {
	    when(trainRepository.findById("NotFound")).thenReturn(Optional.empty());
	    
	    // Call the service method and expect a TrainNotFoundException
	    assertThrows(TrainNotFoundException.class, () -> {
	        trainServiceImpl.getTrainById("Train NotFound");
	    });
	}

	@Test
	public void getTrainsByName_DataFound() {
		TrainModel train = new TrainModel("1", "Express Train", "Nandyal", "Banglore", 67, 67, "10:00 AM");
		when(trainRepository.findByTrainName("Express Train")).thenReturn(List.of(train));
		List<TrainModel> result = trainServiceImpl.getTrainsbyname("Express Train");
		assertEquals(train, result.get(0));
	}

	@Test
	public void getTrainsByName_DataNotFound() {
		
	    when(trainRepository.findByTrainName("NotFound")).thenReturn(new ArrayList<>());
	    
	    // Call the service method and expect a TrainNotFoundException
	    assertThrows(TrainNotFoundException.class, () -> {
	        trainServiceImpl.getTrainsbyname("Train NotFound");
	    });
	}

	@Test
	public void findByLocation_DataFound() {
		TrainModel train = new TrainModel("1", "Express Train", "Nandyal", "Banglore", 67, 67, "10:00 AM");
		List<TrainModel> expectedResults = List.of(train);
		when(trainRepository.findByTrainFromAndTrainTo("Nandyal", "Banglore")).thenReturn(expectedResults);
		List<TrainModel> result = trainServiceImpl.findByLocation("Nandyal", "Banglore");
		assertEquals(expectedResults, result);
	}

	@Test
	public void findByLocation_DataNotFound() {
	  
	    when(trainRepository.findByTrainFromAndTrainTo("aaa", "bbb")).thenReturn(new ArrayList<>());

	    // Call the service method and expect a TrainNotFoundException
	    assertThrows(TrainNotFoundException.class, () -> {
	        trainServiceImpl.findByLocation("aaa", "bbb");
	    });
	}
	
	@Test
	public void deleteTrain_Exists() {
	    TrainModel train = new TrainModel("1", "Express Train", "Nandyal", "Banglore", 67, 100, "10:00 AM");

	    when(trainRepository.findById("1")).thenReturn(Optional.of(train));
	    String result = trainServiceImpl.deleteTrain("1");
	    assertEquals("Train is deleted Successfully", result);
	}

	@Test
	public void deleteTrain_NotExists() {
	    // Stub the behavior of trainRepository.findById to return an empty Optional (train doesn't exist)
	    when(trainRepository.findById("2")).thenReturn(Optional.empty());

	    // Call the service method and expect a TrainNotFoundException
	    assertThrows(TrainNotFoundException.class, () -> {
	        trainServiceImpl.deleteTrain("2");
	    });
	}
}
