package com.Cathouse.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class IdCardValidator {

    private static final Map<String, String> PROVINCE_CODES = new HashMap<>();
    private static final int[] WEIGHTING_FACTORS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final char[] CHECK_CODES = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    static {
        PROVINCE_CODES.put("11", "北京");
        PROVINCE_CODES.put("12", "天津");
        PROVINCE_CODES.put("13", "河北");
        PROVINCE_CODES.put("14", "山西");
        PROVINCE_CODES.put("15", "内蒙古");
        PROVINCE_CODES.put("21", "辽宁");
        PROVINCE_CODES.put("22", "吉林");
        PROVINCE_CODES.put("23", "黑龙江");
        PROVINCE_CODES.put("31", "上海");
        PROVINCE_CODES.put("32", "江苏");
        PROVINCE_CODES.put("33", "浙江");
        PROVINCE_CODES.put("34", "安徽");
        PROVINCE_CODES.put("35", "福建");
        PROVINCE_CODES.put("36", "江西");
        PROVINCE_CODES.put("37", "山东");
        PROVINCE_CODES.put("41", "河南");
        PROVINCE_CODES.put("42", "湖北");
        PROVINCE_CODES.put("43", "湖南");
        PROVINCE_CODES.put("44", "广东");
        PROVINCE_CODES.put("45", "广西");
        PROVINCE_CODES.put("46", "海南");
        PROVINCE_CODES.put("50", "重庆");
        PROVINCE_CODES.put("51", "四川");
        PROVINCE_CODES.put("52", "贵州");
        PROVINCE_CODES.put("53", "云南");
        PROVINCE_CODES.put("54", "西藏");
        PROVINCE_CODES.put("61", "陕西");
        PROVINCE_CODES.put("62", "甘肃");
        PROVINCE_CODES.put("63", "青海");
        PROVINCE_CODES.put("64", "宁夏");
        PROVINCE_CODES.put("65", "新疆");
        PROVINCE_CODES.put("81", "香港");
        PROVINCE_CODES.put("82", "澳门");
        PROVINCE_CODES.put("83", "台湾");
    }
    public static boolean validate(String idCard) {
        if (!checkIdCardFormat(idCard)) {
            return false;
        }
        String provinceCode = idCard.substring(0, 2);
        if (!PROVINCE_CODES.containsKey(provinceCode)) {
            return false;
        }
        if (!checkBirthday(idCard)) {
            return false;
        }
        return checkLastDigit(idCard);
    }
    private static boolean checkIdCardFormat(String idCard) {
        if (idCard == null || idCard.length() != 18) {
            return false;
        }
        String first17 = idCard.substring(0, 17);
        if (!Pattern.matches("\\d{17}", first17)) {
            return false;
        }
        char lastChar = idCard.charAt(17);
        return Character.isDigit(lastChar) || lastChar == 'X' || lastChar == 'x';
    }
    private static boolean checkBirthday(String idCard) {
        String birthdayStr = idCard.substring(6, 14);

        try {
            int year = Integer.parseInt(birthdayStr.substring(0, 4));
            int month = Integer.parseInt(birthdayStr.substring(4, 6));
            int day = Integer.parseInt(birthdayStr.substring(6, 8));
            if (year < 1900 || year > 2100) return false;
            if (month < 1 || month > 12) return false;
            if (day < 1 || day > 31) return false;
            return isValidDate(year, month, day);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private static boolean isValidDate(int year, int month, int day) {
        if (month == 2) {
            boolean isLeapYear = (year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
            return isLeapYear ? day <= 29 : day <= 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return day <= 30;
        }
        return true;
    }
    private static boolean checkLastDigit(String idCard) {
        char[] idChars = idCard.toCharArray();
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (idChars[i] - '0') * WEIGHTING_FACTORS[i];
        }
        int checkIndex = sum % 11;
        char checkCode = CHECK_CODES[checkIndex];
        return Character.toUpperCase(idChars[17]) == checkCode;
    }
}