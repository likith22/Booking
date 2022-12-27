package com.ola.booking.repository;

import java.sql.Date;
import java.sql.Time;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ola.booking.entity.BookingHistory;

@Repository
public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Integer> {
	
}
