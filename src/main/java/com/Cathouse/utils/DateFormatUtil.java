package com.Cathouse.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatUtil {

    private static final DateTimeFormatter CHINESE_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("MM月dd日");
    public static LocalDate parseChineseDate(String dateStr) {
        try {
            int currentYear = LocalDate.now().getYear();
            return LocalDate.parse(currentYear + "年" + dateStr,
                    DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("日期格式不正确，应为'MM月dd日'格式: " + dateStr);
        }
    }
    public static String formatToChineseDate(LocalDate date) {
        return date.format(CHINESE_DATE_FORMATTER);
    }
}