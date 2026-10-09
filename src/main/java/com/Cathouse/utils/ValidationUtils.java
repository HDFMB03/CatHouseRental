package com.Cathouse.utils;
import org.springframework.util.StringUtils;
import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");
    private static final Pattern MOBILE_PATTERN = Pattern.compile(
            "^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$"
    );
    private static final Pattern INTERNATIONAL_MOBILE_PATTERN = Pattern.compile(
            "^\\+(?:[0-9] ?){6,14}[0-9]$"
    );
    public static boolean isValidChineseMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        return MOBILE_PATTERN.matcher(mobile).matches();
    }
    public static boolean isValidInternationalMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        return INTERNATIONAL_MOBILE_PATTERN.matcher(mobile).matches();
    }
    public static boolean isValidMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        if (mobile.startsWith("1") && mobile.length() == 11) {
            return isValidChineseMobile(mobile);
        }
        if (mobile.startsWith("+")) {
            return isValidInternationalMobile(mobile);
        }
        return false;
    }
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    public static boolean containsChinese(String input) {
        if (StringUtils.isEmpty(input)) {
            return false;
        }
        return CHINESE_PATTERN.matcher(input).find();
    }
}
