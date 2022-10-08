package org.acme.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    private static final String EVENT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static String dateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat(EVENT_DATE_FORMAT);
        return formatter.format(date);
    }

    public static Date stringToDate(String date) throws ParseException {
        return new SimpleDateFormat(EVENT_DATE_FORMAT).parse(date);
    }

}
