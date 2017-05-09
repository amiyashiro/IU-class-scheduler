package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Adam on 3/3/2017.
 */

public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    static public ArrayList<Day> toArrayList(String days) {
        ArrayList<Day> dayArray = new ArrayList<Day>();

        String[] stringArray = days.substring(1, days.length()-1).split(", ");

        ArrayList<String> strArrayList = new ArrayList<String>(Arrays.asList(stringArray));

        // convert strings to sections
        for (String day : strArrayList) {
            dayArray.add(Day.valueOf(day));
        }

        return dayArray;
    }

    static String toShortString(ArrayList<Day> days) {
        String shortDays = "";
        for (Day day : days) {
            switch (day) {
                case MONDAY:
                    shortDays += "M, ";
                    break;

                case TUESDAY:
                    shortDays += "T, ";
                    break;

                case WEDNESDAY:
                    shortDays += "W, ";
                    break;

                case THURSDAY:
                    shortDays += "TH, ";
                    break;

                case FRIDAY:
                    shortDays += "F, ";
                    break;

                case SATURDAY:
                    break;

                case SUNDAY:
                    break;
            }
        }
        // cut off space and comma
        shortDays = shortDays.substring(0, shortDays.length()-2);
        return shortDays;
    }
}
