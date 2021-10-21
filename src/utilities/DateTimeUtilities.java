package utilities;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTimeUtilities {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy h:mm a z");
    private static LocalDate date;
    private static LocalTime time;
    private static LocalDateTime localDateTime;
    private static ZonedDateTime zonedDateTime;
    private static String formattedDateTime;

    public static String convertToLocalDateTime(LocalDate localDate, LocalTime localTime) {
        date = localDate;
        time = localTime;
        localDateTime = LocalDateTime.of(date, time);
        zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        formattedDateTime = zonedDateTime.ofInstant(zonedDateTime.toInstant(), ZoneId.systemDefault()).format(formatter);
        return formattedDateTime;
    }

    public static String nowToUTCDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        String dateTimeString = localDate.toString() + " " + localTime.toString();
        return dateTimeString;
    }
}
