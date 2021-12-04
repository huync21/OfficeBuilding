package com.example.officebuilding.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
    public static String getDateWithoutTime(String dateWithTime){
        String result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        try {
            result = sdf1.format(sdf.parse(dateWithTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
