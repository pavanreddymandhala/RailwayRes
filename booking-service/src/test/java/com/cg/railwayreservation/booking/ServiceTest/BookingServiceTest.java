package com.cg.railwayreservation.booking.ServiceTest;
import com.cg.railwayreservation.booking.ExternalClasses.LoginModel;
import com.cg.railwayreservation.booking.ExternalClasses.TrainBookingVo;
import com.cg.railwayreservation.booking.ExternalClasses.TrainModel;
import com.cg.railwayreservation.booking.externalServices.LoginProxy;
import com.cg.railwayreservation.booking.externalServices.PaymentProxy;
import com.cg.railwayreservation.booking.externalServices.TrainProxy;
import com.cg.railwayreservation.booking.model.BookingModel;

import com.cg.railwayreservation.booking.repository.BookingRepository;
import com.cg.railwayreservation.booking.service.BookingService;
import com.cg.railwayreservation.booking.serviceimpl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TrainProxy trainProxy;

    @Mock
    private LoginProxy loginProxy;
    
    @Mock
    private PaymentProxy paymentproxy;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBookTicket() {
        // Mock data
        BookingModel booking = new BookingModel();
        booking.setUsername("user1");
        booking.setTrainNo("12345");
        booking.setNumberOfTickets(2);

        when(trainProxy.getTrainByNo("12345")).thenReturn(new TrainModel("12345", "Train 1", "City A","City B", 100, 50, "10:00 AM"));

        when(bookingRepository.save(booking)).thenReturn(booking);

        String result = bookingService.bookTicket(booking);
        
        verify(bookingRepository, times(1)).save(booking);

        // Verify that the paymentProxy's doPayment method was called with the correct arguments

         verify(paymentproxy, times(1)).doPayment("user1", booking.getPnr(), 200);
    }
    
   
    @Test
    public void testCancelTicket() {
        // Mock data
        String pnr = "PNR123";
        BookingModel booking = new BookingModel();
        booking.setPnr(pnr);
        booking.setTrainNo("12345");
        booking.setNumberOfTickets(2);

        when(bookingRepository.findByPnr(pnr)).thenReturn(booking);
        when(bookingRepository.deleteByPnr(pnr)).thenReturn("Successfully Cancel the Ticket");

        when(trainProxy.getTrainByNo("12345")).thenReturn(new TrainModel("12345", "Train 1", "City A","City B", 100, 50, "10:00 AM"));

        String result = bookingService.cancelTicket(pnr);

        assertEquals("Successfully Cancel the Ticket", result);
    }

    @Test
    public void testGetAllBookings() {
        // Mock data
        BookingModel booking1 = new BookingModel();
        booking1.setPnr("PNR123");
        BookingModel booking2 = new BookingModel();
        booking2.setPnr("PNR456");
        when(bookingRepository.findAll()).thenReturn(List.of(booking1, booking2));

        // Perform the test
        List<BookingModel> result = bookingService.getAllBookings();

        // Verify the result
        assertEquals(2, result.size());
    }

    @Test
    public void testGetBookingByPNR() {
        
        String pnr = "PNR123";
        BookingModel booking = new BookingModel();
        booking.setPnr(pnr);
        when(bookingRepository.findByPnr(pnr)).thenReturn(booking);

        BookingModel result = bookingService.getBookingByPNR(pnr);

        assertEquals(pnr, result.getPnr());
    }
    
    @Test

    public void testGetBookingByUsername() {

        String username = "user1";

        BookingModel booking1 = new BookingModel();

        booking1.setUsername(username);

        BookingModel booking2 = new BookingModel();

        booking2.setUsername(username);
        List<BookingModel> allBookings = new ArrayList<>();

        allBookings.add(booking1);

        allBookings.add(booking2);

        when(bookingRepository.findAll()).thenReturn(allBookings);

        List<BookingModel> result = bookingService.getBookingByUsername(username);


        assertEquals(2, result.size()); // Expecting two bookings for the given username

        assertTrue(result.contains(booking1)); // Expecting booking1 to be in the result list

        assertTrue(result.contains(booking2)); // Expecting booking2 to be in the result list

    }

    @Test
    public void testGetTicketDetailsWithTrainDetails() {
        String pnr = "PNR123";
        
        // Create a BookingModel object
        BookingModel booking = new BookingModel();
        booking.setPnr(pnr);
        booking.setUsername("user1");
        booking.setTrainNo("12345");
        
        // Create a TrainModel object
        TrainModel train = new TrainModel("12345", "Train 1", "City A", "City B", 100, 50, "10:00 AM");
        
        // Create a LoginModel object
        LoginModel login = new LoginModel("user1","user","User","user@gmail.com","Male",23,"India");
        
        // Mock the behavior of bookingRepository.findByPnr
        when(bookingRepository.findByPnr(pnr)).thenReturn(booking);
        
        // Mock the behavior of trainProxy.getTrainByNo
        when(trainProxy.getTrainByNo("12345")).thenReturn(train);
        
        // Mock the behavior of loginProxy.getbyUserName
        when(loginProxy.getbyUserName("user1")).thenReturn(login);
        
        // Call the method to be tested
        TrainBookingVo result = bookingService.getTicketDetailsWithTrainDetaisl(pnr);
        
        // Verify that the method behaves as expected
        assertEquals(train.getTrainName(), result.getTrainModel().getTrainName());
        assertEquals(booking.getUsername(), result.getLoginModel().getUsername());
        
        // Verify that bookingRepository.save is called once with the booking object
        verify(bookingRepository, times(1)).save(booking);
    }
   
}
