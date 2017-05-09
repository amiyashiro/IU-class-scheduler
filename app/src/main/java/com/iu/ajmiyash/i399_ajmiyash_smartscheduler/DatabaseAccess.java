package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.util.ArrayList;

/**
 * Created by Adam on 4/7/2017.
 */

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;

    // Terms table
    private static final String TABLE_TERMS="Terms";
    private static final String COLUMN_TERMID="term_id";
    private static final String COLUMN_YEAR="year";
    private static final String COLUMN_SEASON="season";

    // Courses table
    private static final String TABLE_COURSES="Courses";
    private static final String COLUMN_COURSEID="course_id";
    private static final String COLUMN_DEPARTMENT="department";
    private static final String COLUMN_CODE="code";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_CREDITHOURS="credit_hours";
    private static final String COLUMN_SECTIONS="sections";

    // Classes table
    private static final String TABLE_CLASSES="Classes";
    private static final String COLUMN_CLASSID="class_id";
    private static final String COLUMN_STARTTIME="start_time";
    private static final String COLUMN_ENDTIME="end_time";
    private static final String COLUMN_DAYS="days";
    private static final String COLUMN_PROFESSOR="professor";
    private static final String COLUMN_SECTION="section";
    private static final String COLUMN_BUILDING="building";
    private static final String COLUMN_ROOM="room";
    private static final String COLUMN_SEATS="seats";
    private static final String COLUMN_AVAILABLE="available";
    private static final String COLUMN_WAITLIST="waitlist";
    private static final String COLUMN_TOPIC="topic";
    private static final String COLUMN_RELATION="relation";
    private static final String COLUMN_PERIOD="period";

    // Buildings table
    private static final String TABLE_BUILDINGS="Buildings";
    private static final String COLUMN_BUILDINGID="building_id";
    private static final String COLUMN_BUILDING_NAME="name";
    private static final String COLUMN_LATITUDE="latitude";
    private static final String COLUMN_LONGITUDE="longitude";


    private DatabaseAccess(Context context) {
        this.openHelper = new DBHandler(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.db = openHelper.getReadableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
        }
    }


    public Term findTerm(int year, Season season) {
        String sqlQuery = "SELECT * FROM " + TABLE_TERMS +
                " WHERE " + COLUMN_YEAR + " = \"" + year + "\"" +
                " AND " + COLUMN_SEASON + " = \"" + season.name() + "\"";

        open();

        Cursor myCursor = db.rawQuery(sqlQuery, null);
        Term myTerm = null;

        if (myCursor.moveToFirst()) {
            int tmpID = myCursor.getInt(0);
            int tmpYear = myCursor.getInt(1);
            Season tmpSeason = Season.valueOf(myCursor.getString(2));
            myCursor.close();
            myTerm = new Term(tmpID, tmpYear, tmpSeason);
        }

        close();

        return myTerm;
    }

    public Course findCourse(Term term, String department, String code) {
        String sqlQuery = "SELECT * FROM " + TABLE_COURSES +
                " WHERE " + COLUMN_DEPARTMENT + " = \"" + department + "\"" +
                " AND " + COLUMN_CODE + " = \"" + code + "\"" +
                " AND " + COLUMN_TERMID + " = \"" + term.getmTermId() + "\"";

        open();

        Cursor myCursor = db.rawQuery(sqlQuery, null);
        Course myCourse = null;

        if (myCursor.moveToFirst()) {
            int tmpID = myCursor.getInt(0);
            int tmpTermID = myCursor.getInt(1);
            String tmpDepartment = myCursor.getString(2);
            String tmpCode = myCursor.getString(3);
            String tmpName = myCursor.getString(4);
            double tmpCreditHours = myCursor.getDouble(5);
            String tmpSections = myCursor.getString(6);
            ArrayList<Section> sections = Section.toArrayList(tmpSections);
            myCursor.close();
            myCourse = new Course(tmpID, tmpTermID, tmpDepartment, tmpCode, tmpName, tmpCreditHours, sections);
        }

        close();

        return myCourse;
    }

    public ArrayList<Course> findCourses(Term term, String query) {
        ArrayList<Course> courses = new ArrayList<Course>();
        String sqlQuery;

        open();

        if (query.matches("")) {
            sqlQuery = "SELECT * FROM " + TABLE_COURSES +
                    " WHERE " + COLUMN_TERMID + " = \"" + term.getmTermId() + "\"";
        } else {
            sqlQuery = "SELECT * FROM " + TABLE_COURSES +
                    " WHERE " + COLUMN_TERMID + " = \"" + term.getmTermId() + "\"" +
                    " AND (" + COLUMN_DEPARTMENT + " = \"" + query + "\"" +
                    " OR " + COLUMN_CODE + " = \"" + query + "\"" +
                    " OR " + COLUMN_NAME + " = \"" + query + "\")";
        }

        Cursor myCursor = db.rawQuery(sqlQuery, null);
        myCursor.moveToFirst();

        while (!myCursor.isAfterLast()) {
            int tmpID = myCursor.getInt(0);
            int tmpTermID = myCursor.getInt(1);
            String tmpDepartment = myCursor.getString(2);
            String tmpCode = myCursor.getString(3);
            String tmpName = myCursor.getString(4);
            double tmpCreditHours = myCursor.getDouble(5);
            String tmpSections = myCursor.getString(6);
            ArrayList<Section> sections = Section.toArrayList(tmpSections);
            courses.add(new Course(tmpID, tmpTermID, tmpDepartment, tmpCode, tmpName, tmpCreditHours, sections));
            myCursor.moveToNext();
        }
        myCursor.close();
        close();

        return courses;
    }

    public Class findClass(Course course, String professor, Section section, String building, int room) {
        String sqlQuery = "SELECT * FROM " + TABLE_CLASSES +
                " WHERE " + COLUMN_COURSEID + " = \"" + course.getmCourseId() + "\"" +
                " AND " + COLUMN_PROFESSOR + " = \"" + professor + "\"" +
                " AND " + COLUMN_SECTION + " = \"" + section.name() + "\"" +
                " AND " + COLUMN_BUILDING + " = \"" + building + "\"" +
                " AND " + COLUMN_ROOM + " = \"" + room + "\"";

        open();

        Cursor myCursor = db.rawQuery(sqlQuery, null);
        Class myClass = null;

        if (myCursor.moveToFirst()) {
            int tmpID = myCursor.getInt(0);
            int tmpCourseID = myCursor.getInt(1);
            String tmpStartTime = myCursor.getString(2);
            String tmpEndTime = myCursor.getString(3);
            String tmpDays = myCursor.getString(4);
            String tmpProfessor = myCursor.getString(5);
            Section tmpSection = Section.valueOf(myCursor.getString(6));
            String tmpBuilding = myCursor.getString(7);
            int tmpRoom = myCursor.getInt(8);
            int tmpSeats = myCursor.getInt(9);
            int tmpAvailable = myCursor.getInt(10);
            int tmpWaitlist = myCursor.getInt(11);
            String tmpTopic = myCursor.getString(12);
            int tmpRelation = myCursor.getInt(13);
            Period tmpPeriod = Period.valueOf(myCursor.getString(14));

            myCursor.close();

            ClassTime startTime = ClassTime.valueOf(tmpStartTime);
            ClassTime endTime = ClassTime.valueOf(tmpEndTime);
            ArrayList<Day> days = Day.toArrayList(tmpDays);
            if (tmpTopic == null) {
                myClass = new Class(tmpID, tmpCourseID, startTime, endTime, days, tmpProfessor, tmpSection,
                        tmpBuilding, tmpRoom, tmpSeats, tmpAvailable, tmpWaitlist, tmpRelation, tmpPeriod);
            } else {
                myClass = new Class(tmpID, tmpCourseID, startTime, endTime, days, tmpProfessor, tmpSection,
                        tmpBuilding, tmpRoom, tmpSeats, tmpAvailable, tmpWaitlist, tmpTopic, tmpRelation, tmpPeriod);
            }
        }

        close();
        return myClass;
    }

    /** Finds all classes for a given course **/
    public ArrayList<Class> findClasses(Course course) {
        ArrayList<Class> classes = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + TABLE_CLASSES +
                " WHERE " + COLUMN_COURSEID + " = \"" + course.getmCourseId() + "\"";

        open();

        Cursor myCursor = db.rawQuery(sqlQuery, null);
        myCursor.moveToFirst();

        while (!myCursor.isAfterLast()) {
            int tmpID = myCursor.getInt(0);
            int tmpCourseID = myCursor.getInt(1);
            String tmpStartTime = myCursor.getString(2);
            String tmpEndTime = myCursor.getString(3);
            String tmpDays = myCursor.getString(4);
            String tmpProfessor = myCursor.getString(5);
            Section tmpSection = Section.valueOf(myCursor.getString(6));
            String tmpBuilding = myCursor.getString(7);
            int tmpRoom = myCursor.getInt(8);
            int tmpSeats = myCursor.getInt(9);
            int tmpAvailable = myCursor.getInt(10);
            int tmpWaitlist = myCursor.getInt(11);
            String tmpTopic = myCursor.getString(12);
            int tmpRelation = myCursor.getInt(13);
            Period tmpPeriod = Period.valueOf(myCursor.getString(14));

            ClassTime startTime = ClassTime.valueOf(tmpStartTime);
            ClassTime endTime = ClassTime.valueOf(tmpEndTime);
            ArrayList<Day> days = Day.toArrayList(tmpDays);

            Class myClass;
            if (tmpTopic == null) {
                myClass = new Class(tmpID, tmpCourseID, startTime, endTime, days, tmpProfessor, tmpSection,
                        tmpBuilding, tmpRoom, tmpSeats, tmpAvailable, tmpWaitlist, tmpRelation, tmpPeriod);
            } else {
                myClass = new Class(tmpID, tmpCourseID, startTime, endTime, days, tmpProfessor, tmpSection,
                        tmpBuilding, tmpRoom, tmpSeats, tmpAvailable, tmpWaitlist, tmpTopic, tmpRelation, tmpPeriod);
            }

            classes.add(myClass);
            myCursor.moveToNext();
        }
        myCursor.close();
        close();

        return classes;
    }

    /** Find building based on building name, return instance of Building object **/
    public Building findBuilding(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_BUILDINGS + " WHERE " + COLUMN_BUILDING_NAME +
                " = \"" + name + "\"";

        open();

        Cursor myCursor = db.rawQuery(sqlQuery, null);
        myCursor.moveToFirst();
        double latitude = myCursor.getDouble(2);
        double longitude = myCursor.getDouble(3);

        Building building = new Building(name, latitude, longitude);

        myCursor.close();
        close();
        return building;
    }
}
