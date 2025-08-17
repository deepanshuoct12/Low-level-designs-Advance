package org.dynamik.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Show {
    private String id;
    private String movieId;
    private String theatreId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
