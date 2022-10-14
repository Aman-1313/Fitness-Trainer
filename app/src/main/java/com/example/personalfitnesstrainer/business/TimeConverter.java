package com.example.personalfitnesstrainer.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeConverter {

    public static String timeConvertLongToString(long toConvert){
        String result = "-";
        if(toConvert > 0){
            Calendar toConvertCalendar = Calendar.getInstance();
            toConvertCalendar.setTimeInMillis(toConvert);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            TimeZone currTimeZone = TimeZone.getTimeZone("America/Chicago");
            sdf.setTimeZone(currTimeZone);
            result = sdf.format(toConvertCalendar.getTime());
        }
        return result;
    }

    public static long calendarToLong(Calendar toConvert){
        return toConvert.getTimeInMillis();
    }

    public static long getTimeInLong(int year , int month , int day , int hour , int minute , int second){
        long result = -1;
        if(timeValidation(year, month, day, hour, minute, second)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            String timeFormat = getDateFormat(year, month, day, hour, minute, second);
            try {
                date = sdf.parse(timeFormat);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar timeCal = Calendar.getInstance();
            timeCal.setTime(date);
            result = calendarToLong(timeCal);
        }
        return result;
    }

    public static String getDateFormat(int year , int month , int day , int hour , int minute , int second){
        String result = "";
        if(timeValidation(year, month, day, hour, minute, second)){
            result += year + "-";
            result += month + "-";
            result += day + " ";
            result += hour + ":";
            result += minute + ":";
            result += second;
        }

        return result;
    }

    public static boolean timeValidation(int year , int month , int day , int hour , int minute , int second){
        boolean result = year > 0 && month > 0 && month <= 12 && day > 0 && day <= 31 && hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59 && second > 0 && second <= 59;
        return result;
    }
}