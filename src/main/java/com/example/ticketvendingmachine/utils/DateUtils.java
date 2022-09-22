package com.example.ticketvendingmachine.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static LocalDateTime getFormattedDateTimeNow() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return formatDateTime(localDateTime);
    }
    public static LocalDateTime formatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd**'T'**HH:mm:ss");
        String formatDateTime = localDateTime.format(formatter);
        return LocalDateTime.parse(formatDateTime, formatter);
    }


}
