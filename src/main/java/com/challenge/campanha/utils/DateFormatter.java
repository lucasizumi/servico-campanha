package com.challenge.campanha.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    private static final String DATE_PATTERN = "dd/MM/YYYY";

    public static String format(LocalDate localDate) {
        return DateTimeFormatter.ofPattern(DATE_PATTERN).format(localDate);
    }

	
}
