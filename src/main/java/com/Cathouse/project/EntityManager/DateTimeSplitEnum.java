package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public enum DateTimeSplitEnum {

    SPLIT_1(1, "9:00-10:00"),
    SPLIT_2(2, "10:00-11:00"),
    SPLIT_3(3, "11:00-12:00"),
    SPLIT_4(4, "15:00-16:00"),
    SPLIT_5(5, "16:00-17:00"),
    SPLIT_6(6, "17:00-18:00");

    private final Integer type;
    private final String detail;

    public static String getDetail(Integer type) {
        for (DateTimeSplitEnum value : DateTimeSplitEnum.values()) {
            if (value.getType().equals(type)) {
                return value.getDetail();
            }
        }
        return "其他";
    }

    public static boolean isTimeSlotValid(String slotTimeSplit, LocalDate bookingDate) {

        LocalDateTime now = LocalDateTime.now();
        if (!bookingDate.isEqual(now.toLocalDate())) {
            return true;
        }
        String endTimeStr = slotTimeSplit.split("-")[1];
        LocalTime endTime = LocalTime.parse(endTimeStr);
        return !now.toLocalTime().isAfter(endTime);
    }

}
