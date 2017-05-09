package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import java.util.Date;

/**
 * Created by Adam on 3/3/2017.
 */

public class Term {
    private int mTermId;
    private int mYear;
    private Season mSeason;

    public Term(int year, Season season) {
        mYear = year;
        mSeason = season;
    }

    public Term(int termId, int year, Season season) {
        mTermId = termId;
        mYear = year;
        mSeason = season;
    }

    public int getmTermId() {
        return mTermId;
    }

    public void setmTermId(int mTermId) {
        this.mTermId = mTermId;
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public Season getmSeason() {
        return mSeason;
    }

    public void setmSeason(Season mSeason) {
        this.mSeason = mSeason;
    }
}
