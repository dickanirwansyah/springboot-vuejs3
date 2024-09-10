package com.app.student_service.util;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class ConvertDate {

    public static String convertDateUTCtoTimeZone(String dateUtc){
        //parse the input UTC date string
        ZonedDateTime utcZonedDateTime = ZonedDateTime.parse(dateUtc, DateTimeFormatter.ISO_DATE_TIME);
        //convert to asia/jakarta
        ZonedDateTime localZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Jakarta"));
        //format date to string
        String formattedDate = localZonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        log.info("formatdate from UTC : {} - TimeZone : {}",dateUtc,formattedDate);
        return formattedDate;
    }

    public static String convertTimeZoneToUTC(Date dateInTimeZone) {
        // Convert the java.util.Date to an Instant
        Instant instant = dateInTimeZone.toInstant();

        // Create a ZonedDateTime from the Instant, assuming the input timezone (e.g., Asia/Jakarta)
        ZonedDateTime localZonedDateTime = instant.atZone(ZoneId.of("Asia/Jakarta"));

        // Convert the ZonedDateTime to UTC
        ZonedDateTime utcZonedDateTime = localZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));

        // Format the UTC date to string
        String formattedDate = utcZonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));


        return formattedDate;
    }
}
