package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import java.util.ArrayList;

/**
 * Created by Adam on 3/3/2017.
 */

public class Course {
    private int mCourseId;
    private int mTermId;
    private String mDepartment;
    private String mCode;
    private String mName;
    private double mCreditHours;
    private ArrayList<Section> mSections;

    public Course(int term, String department, String code, String name, double creditHours, ArrayList sections) {
        mTermId = term;
        mDepartment = department;
        mCode = code;
        mName = name;
        mCreditHours = creditHours;
        mSections = sections;
    }

    public Course(int courseId, int term, String department, String code, String name, double creditHours, ArrayList sections) {
        mCourseId = courseId;
        mTermId = term;
        mDepartment = department;
        mCode = code;
        mName = name;
        mCreditHours = creditHours;
        mSections = sections;
    }

    public int getmCourseId() {
        return mCourseId;
    }

    public void setmCourseId(int mCourseId) {
        this.mCourseId = mCourseId;
    }

    public int getmTermId() {
        return mTermId;
    }

    public void setmTermId(int mTermId) {
        this.mTermId = mTermId;
    }

    public String getmDepartment() {
        return mDepartment;
    }

    public void setmDepartment(String mDepartment) {
        this.mDepartment = mDepartment;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public double getmCreditHours() {
        return mCreditHours;
    }

    public void setmCreditHours(double mCreditHours) {
        this.mCreditHours = mCreditHours;
    }

    public ArrayList<Section> getmSections() {
        return mSections;
    }

    public void setmSections(ArrayList mSections) {
        this.mSections = mSections;
    }
}
