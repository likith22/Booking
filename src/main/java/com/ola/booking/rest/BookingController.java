package com.ola.booking.rest;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ola.booking.service.RideBookingService;
import com.ola.booking.template.BookingDetails;
import com.ola.booking.template.BookingRequestBody;
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
		if(bookingRequestBody.getUserName().isEmpty() 
				||bookingRequestBody.getDestination().isEmpty()
				||bookingRequestBody.getCurrentLocation().isEmpty()) {
			Map<String,String> map = new HashMap<>();
			map.put("Status", "Bad Request");
			map.put("Status Code", "400");
			map.put("message", "Required feilds is/are Empty");
			return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
		}
		BookingDetails details = rideBookingService.rideBooking(
				bookingRequestBody.getUserName(),
				bookingRequestBody.getCurrentLocation(),
				bookingRequestBody.getDestination());
		return new ResponseEntity<>(details,HttpStatus.OK);
	}
}
