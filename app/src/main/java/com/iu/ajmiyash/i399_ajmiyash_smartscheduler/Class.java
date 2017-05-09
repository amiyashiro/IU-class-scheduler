package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by Adam on 3/3/2017.
 */

public class Class {
    private int mClassId;
    private int mCourseId;
    private ClassTime mStartTime;
    private ClassTime mEndTime;
    private ArrayList<Day> mDays;
    private String mProfessor;
    private Section mSection;
    private String mBuilding;
    private int mRoom;
    private int mSeats;
    private int mAvailableSeats;
    private int mWaitlist;
    private String mTopic;
    private int mRelation;      // to relate labs and discussions to lectures in a course (0 when not needed)
    private Period mPeriod;     // can be FULL_TERM, FIRST_EIGHT_WEEKS, SECOND_EIGHT_WEEKS

    // no classId
    public Class(int courseId, ClassTime startTime, ClassTime endTime, ArrayList<Day> days, String professor,
                 Section section, String building, int room, int seats, int avail, int wait, String topic,
                 int relation, Period period) {
        mCourseId = courseId;
        mStartTime = startTime;
        mEndTime = endTime;
        mDays = days;
        mProfessor = professor;
        mSection = section;
        mBuilding = building;
        mRoom = room;
        mSeats = seats;
        mAvailableSeats = avail;
        mWaitlist = wait;
        mTopic = topic;
        mRelation = relation;
        mPeriod = period;

    }

    // Topic and classId not needed
    public Class(int courseId, ClassTime startTime, ClassTime endTime, ArrayList<Day> days, String professor,
                 Section section, String building, int room, int seats, int avail, int wait,
                 int relation, Period period) {
        mCourseId = courseId;
        mStartTime = startTime;
        mEndTime = endTime;
        mDays = days;
        mProfessor = professor;
        mSection = section;
        mBuilding = building;
        mRoom = room;
        mSeats = seats;
        mAvailableSeats = avail;
        mWaitlist = wait;
        mRelation = relation;
        mPeriod = period;
    }

    // Constructor with classId and topic
    public Class(int classId, int courseId, ClassTime startTime, ClassTime endTime, ArrayList<Day> days, String professor,
                 Section section, String building, int room, int seats, int avail, int wait, String topic,
                 int relation, Period period) {
        mClassId = classId;
        mCourseId = courseId;
        mStartTime = startTime;
        mEndTime = endTime;
        mDays = days;
        mProfessor = professor;
        mSection = section;
        mBuilding = building;
        mRoom = room;
        mSeats = seats;
        mAvailableSeats = avail;
        mWaitlist = wait;
        mTopic = topic;
        mRelation = relation;
        mPeriod = period;
    }

    // Constructor with classId, without topic
    public Class(int classId, int courseId, ClassTime startTime, ClassTime endTime, ArrayList<Day> days, String professor,
                 Section section, String building, int room, int seats, int avail, int wait,
                 int relation, Period period) {
        mClassId = classId;
        mCourseId = courseId;
        mStartTime = startTime;
        mEndTime = endTime;
        mDays = days;
        mProfessor = professor;
        mSection = section;
        mBuilding = building;
        mRoom = room;
        mSeats = seats;
        mAvailableSeats = avail;
        mWaitlist = wait;
        mRelation = relation;
        mPeriod = period;
    }

    public int getmClassId() {
        return mClassId;
    }

    public void setmClassId(int mClassId) {
        this.mClassId = mClassId;
    }

    public int getmCourseId() {
        return mCourseId;
    }

    public void setmCourseId(int mCourseId) {
        this.mCourseId = mCourseId;
    }

    public ClassTime getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(ClassTime mStartTime) {
        this.mStartTime = mStartTime;
    }

    public ClassTime getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(ClassTime mEndTime) {
        this.mEndTime = mEndTime;
    }

    public ArrayList<Day> getmDays() {
        return mDays;
    }

    public void setmDays(ArrayList<Day> mDays) {
        this.mDays = mDays;
    }

    public String getmProfessor() {
        return mProfessor;
    }

    public void setmProfessor(String mProfessor) {
        this.mProfessor = mProfessor;
    }

    public Section getmSection() {
        return mSection;
    }

    public void setmSection(Section mSection) {
        this.mSection = mSection;
    }

    public String getmBuilding() {
        return mBuilding;
    }

    public void setmBuilding(String mBuilding) {
        this.mBuilding = mBuilding;
    }

    public int getmRoom() {
        return mRoom;
    }

    public void setmRoom(int mRoom) {
        this.mRoom = mRoom;
    }

    public int getmSeats() {
        return mSeats;
    }

    public void setmSeats(int mSeats) {
        this.mSeats = mSeats;
    }

    public int getmAvailableSeats() {
        return mAvailableSeats;
    }

    public void setmAvailableSeats(int mAvailableSeats) {
        this.mAvailableSeats = mAvailableSeats;
    }

    public int getmWaitlist() {
        return mWaitlist;
    }

    public void setmWaitlist(int mWaitlist) {
        this.mWaitlist = mWaitlist;
    }

    public String getmTopic() {
        return mTopic;
    }

    public void setmTopic(String mTopic) {
        this.mTopic = mTopic;
    }

    public int getmRelation() {
        return mRelation;
    }

    public void setmRelation(int mRelation) {
        this.mRelation = mRelation;
    }

    public Period getmPeriod() {
        return mPeriod;
    }

    public void setmPeriod(Period mPeriod) {
        this.mPeriod = mPeriod;
    }
}
