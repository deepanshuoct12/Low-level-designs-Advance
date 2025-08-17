package org.dynamik.model;

import lombok.Data;
import org.dynamik.enums.BookingStatus;

import java.util.List;

@Data
public class Booking {
    private String id;
    private String userId;
    private String showId;
    //private List<String> seatId;
    private BookingStatus status;
}
