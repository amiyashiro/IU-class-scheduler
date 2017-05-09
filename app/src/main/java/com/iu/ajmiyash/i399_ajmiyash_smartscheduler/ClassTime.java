package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

/**
 * Created by Adam on 3/8/2017.
 */

public class ClassTime {
    private int mHour;
    private int mMinute;

    // time represented in 24 hour format
    public ClassTime(int h, int m) {
        mHour = h;
        mMinute = m;
    }

    public ClassTime() {

    }

    // converts the instance of time into string representation 'hh:mm'
    public String toString() {
        String time = String.format("%02d:%02d", mHour, mMinute);

        return time;
    }

    // Overload toString
    // returns string representation of time in 12-hour format if true is passed
    public String toString(boolean twelveHour) {
        if (twelveHour) {
            if (mHour >= 12) {
                if (mHour > 12) {
                    return String.format("%02d:%02dPM", mHour-12, mMinute);
                } else {
                    return String.format("%02d:%02dPM", mHour, mMinute);
                }
            } else {
                return String.format("%02d:%02dAM", mHour, mMinute);
            }
        } else {
            return toString();
        }
    }

    // takes a string representation of a time in 24 hour format 'hh:mm'
    // returns an instance of Time with given values
    static public ClassTime valueOf(String time) {
        int hour = Integer.parseInt(time.substring(0,2));
        int minute = Integer.parseInt(time.substring(3,5));

        ClassTime myTime = new ClassTime(hour, minute);
        return myTime;
    }

    // returns one int representing the time of day, uses both hour and minute values
    public int toValue() {
        return getHour()*100 + getMinute();
    }


    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        this.mHour = hour;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        this.mMinute = minute;
    }
}
