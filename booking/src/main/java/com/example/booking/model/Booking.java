package com.example.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
//import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "booking")
@AllArgsConstructor
public class Booking {
//    @Id
    private String bookingId;
    private Integer roomId;
    private String customerId;
    private Long checkInDate;
    private Long checkOutDate;
}
