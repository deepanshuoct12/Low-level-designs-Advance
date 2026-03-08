package org.dynamik.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Slot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
