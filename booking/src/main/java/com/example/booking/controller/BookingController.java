package com.example.booking.controller;


import com.example.booking.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking/v1")
public class BookingController {

    @Autowired private IBookingService iBookingService;

    @PostMapping("/load-file")
    ResponseEntity<String> parseCsvUpdateDb(@RequestParam String filePath) {
        iBookingService.parseCsvUpdateDb(filePath);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/range")
    ResponseEntity<List<Integer>> getAllBookingsWithinRange(@RequestParam  String checkInDate, @RequestParam  String checkOutDate) {
        List<Integer> bookingDTOList  = iBookingService.getAllBookings(checkInDate, checkOutDate);
        return new ResponseEntity<>(bookingDTOList, HttpStatus.OK);
    }
}
