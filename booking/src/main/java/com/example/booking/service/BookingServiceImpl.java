package com.example.booking.service;

import com.example.booking.dto.BookingDTO;
import com.example.booking.model.Booking;
import com.example.booking.util.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements IBookingService{

    @Autowired private MongoTemplate mongoTemplate;

    @Override
    public String parseCsvUpdateDb(String filePath) {
        List<Booking> bookings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            reader.lines().skip(1).forEach(line -> {
                String[] tokens = line.split(",");
                if (tokens.length == 5) {
                    Booking booking = new Booking(
                            tokens[0],
                            Integer.parseInt(tokens[1]),
                            tokens[2],
                            LocalDate.parse(tokens[3]).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                            LocalDate.parse(tokens[4]).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                    );
                    bookings.add(booking);
                }
            });

            mongoTemplate.insertAll(bookings);
            return "Loaded bookings from file: " + filePath;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading file: " + e.getMessage();
        }
    }

    @Override
    public List<Integer> getAllBookings(String checkInData, String checkOutDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("checkInDate").lte(DateUtility.getDate(checkOutDate)).and("checkOutDate").lt(DateUtility.getDate(checkOutDate)));
        List<Booking> bookings = mongoTemplate.find(query, Booking.class);
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        for (Booking booking:bookings) {
            bookingDTOList.add(new BookingDTO(booking.getBookingId(), booking.getRoomId(), booking.getCustomerId()));
        }

        Map<Integer, Long> roomCount = bookings.stream().collect(Collectors.groupingBy(Booking::getRoomId, Collectors.counting()));
        long maxRoomFreq = roomCount.values().stream().max(Long::compare).orElse(0l);
        List<Integer> mostBookedRooms = roomCount.entrySet().stream()
                .filter(e -> e.getValue() == maxRoomFreq)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return mostBookedRooms;
    }
}
