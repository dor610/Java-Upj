package org.upj.util;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {

    public static boolean validateEmail(String email){
        return Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
                .matcher(email)
                .matches();
    }

    public static Date addDate(Date date, int number) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, number);

        return c.getTime();
    }
}
