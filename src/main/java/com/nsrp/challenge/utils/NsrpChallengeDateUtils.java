package com.nsrp.challenge.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class NsrpChallengeDateUtils {

    private static final String DATE_PATTERN = "dd/MM/YYYY";

    private NsrpChallengeDateUtils() {
    }

    public static String format(LocalDate localDate) {
        return DateTimeFormatter.ofPattern(DATE_PATTERN).format(localDate);
    }
}