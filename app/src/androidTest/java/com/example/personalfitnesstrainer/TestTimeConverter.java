package com.example.personalfitnesstrainer;

import com.example.personalfitnesstrainer.business.TimeConverter;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestTimeConverter {

    @Test
    public void testTimeConvertLongToStringWithCorrectTime(){
        String expectedResult = "2022-03-27 05:05:10";
        String expectedResult2 = "2010-10-10 10:10:10";
        String expectedResult3 = "2002-02-02 02:02:02";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        Date date2 = null;
        Date date3 = null;
        try {
            date = sdf.parse(expectedResult);
            date2 = sdf.parse(expectedResult2);
            date3 = sdf.parse(expectedResult3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar testTime = Calendar.getInstance();
        testTime.setTime(date);
        long testTimeLong = testTime.getTimeInMillis();
        String testTimeString = TimeConverter.timeConvertLongToString(testTimeLong);
        assert(testTimeString.equals(expectedResult));

        testTime.setTime(date2);
        testTimeLong = testTime.getTimeInMillis();
        testTimeString = TimeConverter.timeConvertLongToString(testTimeLong);
        assert(testTimeString.equals(expectedResult2));

        testTime.setTime(date3);
        testTimeLong = testTime.getTimeInMillis();
        testTimeString = TimeConverter.timeConvertLongToString(testTimeLong);
        assert(testTimeString.equals(expectedResult3));
    }

    @Test
    public void testTimeConvertLongToStringWithIncorrectTime(){
        long testIncorrectTimeLong = -123;
        long testIncorrectTimeLong2 = 0;
        String testIncorrectTimeString = TimeConverter.timeConvertLongToString(testIncorrectTimeLong);
        String testIncorrectTimeString2 = TimeConverter.timeConvertLongToString(testIncorrectTimeLong2);
        assert(testIncorrectTimeString.equals("-"));
        assert(testIncorrectTimeString2.equals("-"));
    }

    @Test
    public void testGetTimeInLongWithCorrectDate(){
        long result = TimeConverter.getTimeInLong(2010,10,10 , 10,10,10);
        long expectedResult = 1286723410000L;
        assert(result == expectedResult);
    }


    @Test
    public void testGetTimeInLongWithIncorrectDate(){
        long testIncorrectDate = TimeConverter.getTimeInLong(-2022,-10,-2,0,-9,1);
        assert(testIncorrectDate == -1);
    }

    @Test
    public void getDateFormatWithCorrectFormat(){
        String testFormat = "2022-11-11 11:11:11";
        String result = TimeConverter.getDateFormat(2022,11,11,11,11,11);
        assert(testFormat.equals(result));
    }

    @Test
    public void getDateFormatWithIncorrectFormat(){
        String result = TimeConverter.getDateFormat(-2022,-11,-11,-11,-11,-11);
        assert(result.equals(""));
    }
}
