package ru.lod.spbalert.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * утилитка времени
 */
public class DtUtil {

    public static LocalDateTime toDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }

    public static Date toDate(LocalDateTime dateToConvert) {
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault())
            .toInstant());
    }

}
