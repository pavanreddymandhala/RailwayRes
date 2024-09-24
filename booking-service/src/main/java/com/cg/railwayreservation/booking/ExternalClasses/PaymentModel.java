package com.cg.railwayreservation.booking.ExternalClasses;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class PaymentModel {

	@Id
	private int transactionId;
	private String bookingId;
	private String username;
	private double amount;
	private String transactionStatus;
	
}
