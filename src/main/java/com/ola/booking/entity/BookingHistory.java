package com.ola.booking.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ola.booking.template.BookingHistoryTemplate;

@Entity
@Table(name = "booking_history")
public class BookingHistory {
	
	@Id
	@Column(name = "transaction_id")
	private int transaction_id;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "rider_name")
	private String riderName;
	@Column(name = "current_location")
	private String CurrentLocation;
	@Column(name = "destination")
	private String destination;
	@Column(name = "booking_date")
	private Date bookingDate;
	@Column(name = "booking_time")
	private Time bookingTime;
	
	
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
	
	public BookingHistoryTemplate convertToEntity(BookingHistory transaction) {
		
		BookingHistoryTemplate template = new BookingHistoryTemplate();
		template.setTransaction_id(transaction.getTransaction_id());
		template.setUserName(transaction.getUserName());
		template.setRiderName(transaction.getRiderName());
		template.setCurrentLocation(transaction.getCurrentLocation());
		template.setDestination(transaction.getDestination());
		template.setBookingDate(transaction.getBookingDate());
		template.setBookingTime(transaction.getBookingTime());
		
		return template;
	}
	
}
