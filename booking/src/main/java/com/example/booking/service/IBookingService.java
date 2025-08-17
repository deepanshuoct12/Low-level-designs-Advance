package com.example.booking.service;

import com.example.booking.dto.BookingDTO;

import java.util.List;

public interface IBookingService {
     String parseCsvUpdateDb(String filePath);
     List<Integer> getAllBookings(String checkInData, String checkOutDate);
}
