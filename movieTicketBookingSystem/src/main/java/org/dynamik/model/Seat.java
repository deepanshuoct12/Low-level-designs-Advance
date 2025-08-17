package org.dynamik.model;

import lombok.Data;
import org.dynamik.enums.SeatState;
import org.dynamik.enums.SeatType;

@Data
public class Seat {
    private String id;
    private String showId;
    private SeatType type;
    private SeatState state;
    private String bookingId;
}
