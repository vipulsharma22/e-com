package com.nitsoft.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {
    private EmailUtil() {
    }

    public static boolean isEmailFormat(String valueToValidate) {
            // Regex  
            String regexExpression = "([A-Za-z0-9\\.\\_\\-]+[\\.\\_\\-]*[A-Za-z0-9\\.\\_\\-]*)+@([A-Za-z0-9\\.\\_\\-]+[\\.]*[A-Za-z0-9\\.\\_\\-]+)+\\.[A-Za-z]+";
            Pattern regexPattern = Pattern.compile(regexExpression);
            boolean valid = false;
           if (valueToValidate != null) {
            if (valueToValidate.indexOf("@") <= 0) {
                return false;
            }
            Matcher matcher = regexPattern.matcher(valueToValidate);
            valid = matcher.matches();
        } else { // The case of empty Regex expression must be accepted
                Matcher matcher = regexPattern.matcher("");
                valid = matcher.matches();
            }
            return valid;
    }
}
