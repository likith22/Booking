package com.ola.booking.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ola.booking.exceptions.NoAvailableRidersException;
import com.ola.booking.repository.BookingHistoryRepository;
import com.ola.booking.template.BookingDetails;
import com.ola.booking.template.BookingHistoryTemplate;
import com.ola.booking.template.RiderTemplate;
import com.ola.booking.util.SqlDate;
import com.ola.booking.util.TransactionIdGenerater;

@Service
public class RideBookingServiceImplementation implements RideBookingService {
	
	RestTemplate restCall = new RestTemplate();
	
	@Autowired
	BookingHistoryRepository repository;
	
	TransactionIdGenerater transactionId = new TransactionIdGenerater();
	
	@Override
	public BookingDetails rideBooking(String userName, String currentLocation, String destination) {
		//getting available user list.
		RiderTemplate[] availableRiders =  restCall.getForObject("http://localhost:8091/api/v1/registration/dataRequest/availableRiders", RiderTemplate[].class);
		//checking if availableriders are empty
		if(availableRiders.length == 0) {
			throw new NoAvailableRidersException("No Available riders");
		}
		//finding the least id:
		
		List<RiderTemplate> riders = Arrays.asList(availableRiders);
		Optional<RiderTemplate> orider =riders.stream().min((rider1,rider2)->(int)(rider1.getId() - rider2.getId()));
		RiderTemplate rider = orider.get();
		//generating unique transaction id.
		int transactionId = this.transactionId.buildTransactionId();
		
		//update the transaction in db
		BookingHistoryTemplate template = new BookingHistoryTemplate();
		template.setTransaction_id(transactionId);
		template.setUserName(userName);
		template.setRiderName(rider.getName());
		template.setCurrentLocation(currentLocation);
		template.setDestination(destination);
		template.setBookingDate(SqlDate.currentSqlDate());
		template.setBookingTime(SqlDate.currentSqlTime());
		this.repository.saveAndFlush(template.convertToEntity(template));
		
		//updating the rider status
		String uri = "http://localhost:8091/api/v1/registration/register/changeStatus/riderId/"+rider.getId()+"/status/"+"booked";
		restCall.postForObject(uri, "", Map.class);
		
		//returning booking details
		BookingDetails bookingDetails = new BookingDetails();
		bookingDetails.setBookingStatus("Booked");
		bookingDetails.setRiderEmail(rider.getEmail());
		bookingDetails.setRidername(rider.getName());
		
		return bookingDetails;
	}

}
