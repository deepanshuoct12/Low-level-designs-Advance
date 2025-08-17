package com.example.booking.util;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;

@Service
public abstract class DateUtility {

    public static Long getDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        long epochMillis = localDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        return epochMillis;
    }
}
