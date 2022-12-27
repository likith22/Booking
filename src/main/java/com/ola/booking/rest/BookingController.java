package com.ola.booking.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ola.booking.service.RideBookingService;
import com.ola.booking.template.BookingDetails;
import com.ola.booking.template.BookingRequestBody;
import com.ola.booking.template.RiderTemplate;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class BookingController {
	
	@Autowired
	RideBookingService rideBookingService;
	
	@ApiOperation("Placing a booking request")
	@PostMapping("/BookingRequest")
	public ResponseEntity<?> bookingRequest(@RequestBody BookingRequestBody 
			bookingRequestBody){
		BookingDetails details = rideBookingService.rideBooking(
				bookingRequestBody.getUserName(),
				bookingRequestBody.getCurrentLocation(),
				bookingRequestBody.getDestination());
		return new ResponseEntity<>(details,HttpStatus.OK);
	}
	
	@ApiOperation("Testing Available riders End-Point")
	@GetMapping("/BookingRequest/testAvailableriders")
	public ResponseEntity<?> testingTransactionId(){
		
		String uri = "http://localhost:8091/api/v1/registration/register/changeStatus/riderId/"+1+"/status/"+"available";
		RestTemplate restCall = new RestTemplate();
		restCall .postForObject(uri, "", Map.class);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
}
