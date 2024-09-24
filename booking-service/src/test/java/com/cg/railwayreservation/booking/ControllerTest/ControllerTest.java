package com.cg.railwayreservation.booking.ControllerTest;


import com.cg.railwayreservation.booking.ExternalClasses.TrainBookingVo;

import com.cg.railwayreservation.booking.controller.BookingController;
import com.cg.railwayreservation.booking.model.BookingModel;
import com.cg.railwayreservation.booking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;


    @Test
    public void testBookTicket() {
        BookingModel booking = new BookingModel();
        booking.setUsername("user1");
        booking.setTrainNo("12345");
        booking.setNumberOfTickets(2);

        when(bookingService.bookTicket(booking)).thenReturn("Ticket Confirmed Happy Journey. Your Loved Once is Waiting For You");

        ResponseEntity<String> response = bookingController.bookTicket(booking);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Ticket Confirmed Happy Journey. Your Loved Once is Waiting For You", response.getBody());
        verify(bookingService, times(1)).bookTicket(booking);
    }

    @Test
    public void testCancelTicket() {
        String pnr = "PNR123";

        when(bookingService.cancelTicket(pnr)).thenReturn("Successfully Cancel the Ticket");

        ResponseEntity<String> response = bookingController.cancelTicket(pnr);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Successfully Cancel the Ticket", response.getBody());
        verify(bookingService, times(1)).cancelTicket(pnr);
    }

    @Test
    public void testViewAllBookings() {
        List<BookingModel> bookings = new ArrayList<>();
        
        BookingModel booking1 = new BookingModel();
        booking1.setPnr("PNR1");
        bookings.add(booking1);

        BookingModel booking2 = new BookingModel();
        booking2.setPnr("PNR2");
        bookings.add(booking2);
        when(bookingService.getAllBookings()).thenReturn(bookings);

        ResponseEntity<List<BookingModel>> response = bookingController.viewAllBookings();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bookings, response.getBody());
        assertEquals(2,response.getBody().size());
     //   verify(bookingService, times(1)).getAllBookings();
    }


    @Test
    public void testViewByPnr() {
        String pnr = "PNR123";
        BookingModel booking = new BookingModel();
        booking.setPnr(pnr);

        when(bookingService.getBookingByPNR(pnr)).thenReturn(booking);

        ResponseEntity<BookingModel> response = bookingController.viewByPnr(pnr);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(booking, response.getBody());
        verify(bookingService, times(1)).getBookingByPNR(pnr);
    }
//
//    @Test
//    public void testViewByUserName() {
//        String username = "user1";
//        BookingModel booking = new BookingModel();
//        booking.setUsername(username);
//
//        when(bookingService.getBookingByUsername(username)).thenReturn((List<BookingModel>) booking);
//
//        ResponseEntity<List<BookingModel>> response = bookingController.viewByUserName(username);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(booking, response.getBody());
//        verify(bookingService, times(1)).getBookingByUsername(username);
//    }

    @Test
    public void testGetTicketDetailsWithTrainDetails() {
        String pnr = "PNR123";
        TrainBookingVo trainBookingVo = new TrainBookingVo();
        BookingModel booking = new BookingModel();
        booking.setPnr(pnr);

        when(bookingService.getTicketDetailsWithTrainDetaisl(pnr)).thenReturn(trainBookingVo);

        TrainBookingVo result = bookingController.getBookingTicketByItsTrainAndTotalCost(pnr);

        assertEquals(trainBookingVo, result);
        verify(bookingService, times(1)).getTicketDetailsWithTrainDetaisl(pnr);
    }
}
