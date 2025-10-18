package com.bodms.utils;

import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE = Pattern.compile("^[0-9+()\\- ]{7,20}$");
    private static final Pattern BLOOD = Pattern.compile("^(A|B|AB|O)[+-]$");

    public static boolean isEmail(String s) { return s != null && EMAIL.matcher(s).matches(); }
    public static boolean isPhone(String s) { return s != null && PHONE.matcher(s).matches(); }
    public static boolean isBloodGroup(String s) { return s != null && BLOOD.matcher(s).matches(); }

    public static boolean notBlank(String s) { return s != null && !s.trim().isEmpty(); }
}
