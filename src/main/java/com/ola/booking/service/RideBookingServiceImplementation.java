package com.ola.booking.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ola.booking.entity.BookingHistory;
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
		template.setBookingStatus("pending");
		this.repository.saveAndFlush(template.convertToEntity(template));
		
		//calling the RiderManagetment and updating the RiderBookingRequests
		
		String uri = "http://localhost:8093/api/v1/riderManagment";
		Map<String,String> request = new HashMap<>();
		request.put("transactionId", Integer.toString(transactionId));
		request.put("userCurrentLocation",currentLocation);
		request.put("userDestination", destination);
		request.put("userName", userName);
		request.put("riderName", rider.getName());
		restCall.postForObject(uri, request, Map.class);
		//updating the rider status
//		String uri = "http://localhost:8091/api/v1/registration/register/changeStatus/riderId/"+rider.getId()+"/status/"+"booked";
//		restCall.postForObject(uri, "", Map.class);
		
		//returning booking details
		BookingDetails bookingDetails = new BookingDetails();
		bookingDetails.setBookingStatus("Request is Pending with rider");
		bookingDetails.setRiderEmail(rider.getEmail());
		bookingDetails.setRidername(rider.getName());
		
		return bookingDetails;
	}
	@Override
	public void updateStatus(int transactionId,String status) {
		Optional<BookingHistory> record= this.repository.findById(transactionId);
		record.get().setBookingStatus(status);
		this.repository.saveAndFlush(record.get());
		//update the rider status in rider table so that he is blocked for booking:
		//before that we need to get the rider details:
		String uri = "http://localhost:8091/api/v1/registration/dataRequest/rider/"+record.get().getRiderName();
		RiderTemplate rider = restCall.getForObject(uri, RiderTemplate.class);
		restCall.postForObject("http://localhost:8091/api/v1/registration/register/changeStatus/riderId/"+rider.getId()+"/status/"+status, "", Map.class);
	}
}
