package com.ola.booking.service;

import org.springframework.stereotype.Service;

import com.ola.booking.template.BookingDetails;

@Service
public interface RideBookingService {
	
	public BookingDetails rideBooking(String userName,String currentLocation
			,String destination);
}
