package com.aftebi.mynews.utils;

import android.content.Context;

import com.aftebi.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DayOfTheWeek {

    private Context context;

    public DayOfTheWeek(Context c) {this.context = c;}

    public String getWeek(String date){ //ex 2021-10-03
        String dayWeek = "---";
        String [] days =  context.getResources().getStringArray(R.array.daysOfTheWeek);
        GregorianCalendar gc = new GregorianCalendar();
        try {
            gc.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            switch (gc.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    dayWeek = days[0];
                    break;
                case Calendar.MONDAY:
                    dayWeek = days[1];
                    break;
                case Calendar.TUESDAY:
                    dayWeek = days[2];
                    break;
                case Calendar.WEDNESDAY:
                    dayWeek = days[3];
                    break;
                case Calendar.THURSDAY:
                    dayWeek = days[4];
                    break;
                case Calendar.FRIDAY:
                    dayWeek = days[5];
                    break;
                case Calendar.SATURDAY:
                    dayWeek = days[6];

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayWeek;
    }
}
