package com.crypto.service.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Util {
    
    public static Timestamp getDateHeureMaintenant(){
        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        Timestamp timestamp = Timestamp.from(instant);
        return timestamp;
    }

    public static Date getDateActuelle() {
        // Obtient la date actuelle au format LocalDate
        LocalDate localDate = LocalDate.now();

        // Convertit LocalDate en java.sql.Date
        return Date.valueOf(localDate);
    }
    public static Timestamp getDateTimeActuelle() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return Timestamp.valueOf(localDateTime);
    }
}
