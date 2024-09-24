package com.cg.railwayreservation.booking.externalServices;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cg.railwayreservation.booking.ExternalClasses.PaymentModel;




@FeignClient(name = "PAYMENT-SERVICE", url="http://localhost:8084/payment")
public interface PaymentProxy {

	
	@GetMapping("/doPayment/{userName}/{bookingId}/{amount}")
	public PaymentModel doPayment(@PathVariable String userName,@PathVariable String bookingId,@PathVariable double amount);
	
//	@GetMapping("/getByTransactionId/{transactionId}")
//	public ResponseEntity<Payment> getPayment(@PathVariable int transactionId) throws PaymentNotFoundWithIdException {
//
//	}
}
