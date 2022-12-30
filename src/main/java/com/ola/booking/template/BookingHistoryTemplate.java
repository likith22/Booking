package com.ola.booking.template;

import java.sql.Date;
import java.sql.Time;

import com.ola.booking.entity.BookingHistory;

public class BookingHistoryTemplate {
	
	private int transaction_id;
	private String userName;
	private String riderName;
	private String CurrentLocation;
	private String destination;
	private Date bookingDate;
	private Time bookingTime;
	private String bookingStatus;
	
	
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRiderName() {
		return riderName;
	}
	public void setRiderName(String riderName) {
		this.riderName = riderName;
	}
	public String getCurrentLocation() {
		return CurrentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		CurrentLocation = currentLocation;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public Time getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(Time bookingTime) {
		this.bookingTime = bookingTime;
	}
	
	public BookingHistory convertToEntity(BookingHistoryTemplate template) {
		
		BookingHistory transaction = new BookingHistory();
		transaction.setTransaction_id(template.getTransaction_id());
		transaction.setUserName(template.getUserName());
		transaction.setRiderName(template.getRiderName());
		transaction.setCurrentLocation(template.getCurrentLocation());
		transaction.setDestination(template.getDestination());
		transaction.setBookingDate(template.getBookingDate());
		transaction.setBookingTime(template.getBookingTime());
		transaction.setBookingStatus(template.getBookingStatus());
		
		return transaction;
	}
}
