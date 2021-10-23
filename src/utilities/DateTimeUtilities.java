package utilities;

import java.time.*;
import java.time.format.DateTimeFormatter;

public abstract class DateTimeUtilities {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy h:mm a z");
    private static LocalDate date;
    private static LocalTime time;
    private static LocalDateTime localDateTime;
    private static ZonedDateTime zonedDateTime;
    private static String formattedDateTime;

    public static LocalTime convertToEasternTime(String date, String hour, String minute, String amPM) {
        LocalDateTime localDateTime;
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute));
        if (amPM.equals("PM") && (Integer.parseInt(hour) < 12)) {
            localTime = localTime.plusHours(12);
        }
        localDateTime = LocalDateTime.of(localDate, localTime);
        LocalTime newLocalTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalTime();
        return newLocalTime;
    }


    public static LocalDateTime convertInputToLocalDateTime(String stringDate, String stringHour, String stringMin, String amPM) {
        date = LocalDate.parse(stringDate);
        LocalDateTime localDateTime;
        if (amPM.equals("PM") && (Integer.parseInt(stringHour) < 12)) {
            time = LocalTime.of(Integer.parseInt(stringHour), Integer.parseInt(stringMin)).plusHours(12);
        } else {
            time = LocalTime.of(Integer.parseInt(stringHour), Integer.parseInt(stringMin));
        }
        localDateTime = LocalDateTime.of(date, time);
        return localDateTime;
    }

    public static String getFormattedDateTime(LocalDateTime localDateTime) {
        formattedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).format(formatter);
        return formattedDateTime;
    }
}
