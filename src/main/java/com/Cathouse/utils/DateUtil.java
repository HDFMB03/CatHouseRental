package com.Cathouse.utils;

import com.Cathouse.project.DataTransferObject.QueryDto;
import com.Cathouse.project.ValueObject.ChartVO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM月dd日");
    public static List<String> generateFutureDates(int days) {
        List<String> dateList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < days; i++) {
            LocalDate futureDate = today.plusDays(i);
            dateList.add(futureDate.format(FORMATTER));
        }
        return dateList;
    }
    public static QueryDto startAndEndTime(Integer days) {
        if (days == -1) {
            return new QueryDto();
        }
        if (days < 0) {
            throw new IllegalArgumentException("天数参数不能为负数（除-1外）");
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime;
        if (days == 0) {
            startTime = now.with(LocalTime.MIN);
        } else {
            startTime = now.minusDays(days).with(LocalTime.MIN);
        }
        return QueryDto.builder()
                .startTime(startTime)
                .endTime(now)
                .build();
    }
    public static List<ChartVO> countDatesWithinRange(Integer dayRange, List<LocalDateTime> dates) {
        LocalDate startDate = LocalDate.now().minusDays(dayRange);
        List<ChartVO> chartVOS = new ArrayList<>();
        for (int offset = 0; offset <= dayRange; offset++) {
            LocalDate currentDate = startDate.plusDays(offset);
            String dateKey = String.format("%02d-%02d", currentDate.getMonthValue(), currentDate.getDayOfMonth());
            int count = (int) dates.stream()
                    .filter(dateTime -> dateTime.toLocalDate().equals(currentDate))
                    .count();
            if (count != 0) {
                chartVOS.add(new ChartVO(dateKey, count));
            }
        }
        return chartVOS;
    }
}
