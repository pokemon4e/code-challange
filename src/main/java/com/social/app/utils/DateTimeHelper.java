package com.social.app.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Abstracts away the {@code LocalDateTime.now()} and provides the current date
 * and time in UTC.
 */
@Component
public class DateTimeHelper {

    /**
     * Obtains the current date-time from the system clock in the UTC time-zone.
     *
     * @return the current date-time in UTC using the system clock, not null
     */
    public LocalDateTime nowUTC() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}
