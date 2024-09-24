package com.cg.railwayreservation.booking.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import com.cg.railwayreservation.booking.ExternalClasses.LoginModel;
import com.cg.railwayreservation.booking.ExternalClasses.TrainBookingVo;
import com.cg.railwayreservation.booking.ExternalClasses.TrainModel;
import com.cg.railwayreservation.booking.exception.BookingNotFoundException;
import com.cg.railwayreservation.booking.externalServices.LoginProxy;
import com.cg.railwayreservation.booking.externalServices.PaymentProxy;
import com.cg.railwayreservation.booking.externalServices.TrainProxy;
import com.cg.railwayreservation.booking.model.BookingModel;
import com.cg.railwayreservation.booking.repository.BookingRepository;
import com.cg.railwayreservation.booking.service.BookingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private TrainProxy trainProxy;
	
	@Autowired
	private LoginProxy loginProxy;
	
	@Autowired
	private PaymentProxy paymentproxy;
	
	@Override
	@CircuitBreaker(name = "bookTrainCircuitBreaker",fallbackMethod = "bookTrainFallBack")
	public String bookTicket(BookingModel booking) {
		log.info("Booking a ticket method started inside the BookingServiceImpl Class");
		String pnr = generateUniquePNR();
		// Set the PNR number for the booking
		booking.setPnr(pnr);
		// Implement booking logic, validate input, etc.
    System.out.println("*****************************************************************************");
		String trainNo = booking.getTrainNo();
		TrainModel trainByNo = trainProxy.getTrainByNo(trainNo);

		int numberOfTickets = booking.getNumberOfTickets();
		int seats = trainByNo.getSeats();
		int reamingSets = seats - numberOfTickets;

		trainByNo.setSeats(reamingSets);

		trainProxy.updateTrain(trainNo, trainByNo);

		int fare = trainByNo.getFare();
		int totalcost = fare * numberOfTickets;

		booking.setCost(totalcost);

		BookingModel save = bookingRepository.save(booking);
		
		paymentproxy.doPayment(booking.getUsername(), pnr, totalcost);
		
		return "Ticket Confirmed Happy Journey. Your Loved Once is Waiting For You" + save;
	}

	public String bookTrainFallBack(BookingModel booking,Exception e) {
		return "Microservices are Down";
	}
	
	private String generateUniquePNR() {
		// Generate a random UUID and use it as a PNR number (you can customize this)
		return UUID.randomUUID().toString();
	}

	@Override
	public String cancelTicket(String pnr) {
		log.info("Booking a ticket method started inside the BookingServiceImpl Class");
		BookingModel booking = bookingRepository.findByPnr(pnr);
		if (booking != null) {
			log.info("Inside the if condition of cancelTicket method");

			bookingRepository.deleteByPnr(pnr);

			String trainNo = booking.getTrainNo();
			TrainModel trainByNo = trainProxy.getTrainByNo(trainNo);

			int numberOfTickets = booking.getNumberOfTickets();
			int seats = trainByNo.getSeats();
			int reamingSets = seats + numberOfTickets;

			trainByNo.setSeats(reamingSets);

			trainProxy.updateTrain(trainNo, trainByNo);
			return "Successfully Cancel the Ticket";
		} else {
			log.info("Inside the else condition of cancelTicket method");
			throw new BookingNotFoundException("Pnr not found");
		}
	}

	@Override
	public List<BookingModel> getAllBookings() {
		log.info("getAllBookings method started inside the BookingServiceImpl Class");
		return bookingRepository.findAll();
	}

	@Override
	public BookingModel getBookingByPNR(String pnr) {
		log.info("getBookingByPNR method started inside the BookingServiceImpl Class");
		BookingModel booking = bookingRepository.findByPnr(pnr);
		if (booking != null) {
			log.info("Inside the if condition of getBookingByPNR method");
			return booking;
		} else {
			log.info("Inside the else condition of getBookingByPNR method");
			throw new BookingNotFoundException("No booking was found with the pnr: " + pnr);
		}
	}

	 public List<BookingModel> getBookingByUsername(String username) {
	        log.info("getBookingByUsername method started inside the BookingService class");
	        
	        List<BookingModel> list = new ArrayList<BookingModel>();
	        List<BookingModel> all = bookingRepository.findAll();
//	        BookingModel findByUsername = bookingRepository.findByUsername(username);
	        for(BookingModel b : all) {
	        	
	         String username2 = b.getUsername();
	         if(username2.equals(username)) {
	        	 list.add(b);
	         }
	        }
	        
	        return list;
	    }


	public TrainBookingVo getTicketDetailsWithTrainDetaisl(String pnr) {
		BookingModel booking = bookingRepository.findByPnr(pnr);

		TrainBookingVo vo = new TrainBookingVo();

		if (booking != null) {
			String trainNo = booking.getTrainNo();
			TrainModel trainByNo = trainProxy.getTrainByNo(trainNo);
			
			String username = booking.getUsername();
			 LoginModel userDetails = loginProxy.getbyUserName(username);

			
			
			vo.setTrainModel(trainByNo);
			vo.setBookingModel(booking);
			vo.setLoginModel(userDetails);
			
			bookingRepository.save(booking);

		} else {
			log.warn("Pnr is not Found");
		}
		return vo;
	}

}
