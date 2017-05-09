package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Adam on 3/15/2017.
 */

public class SelectedClasses {
    private ArrayList<Course> mSelectedCourses;
    private Map<Course, ArrayList<Class>> mCoursesToClasses;
    private Map<Course, Map<Section, Class>> mSchedule;

    public SelectedClasses() {
        mSelectedCourses = new ArrayList<>();
        mSchedule = new HashMap<>();
        mCoursesToClasses = new HashMap<>();
    }

    /** Return a list of selected courses **/
    public ArrayList<Course> getmSelectedCourses() {

        return mSelectedCourses;
    }

    /** Return a list of classes for a given course **/
    public ArrayList<Class> getClassesForCourse(Course course) {
        if (mCoursesToClasses == null) {
            return new ArrayList<>();
        } else {
            return mCoursesToClasses.get(course);
        }
    }

    /** Return a list of selected classes **/
    public ArrayList<Class> getSelectedClasses() {
        ArrayList<Class> selectedClasses = new ArrayList<Class>();
        for (Course course : mSchedule.keySet()) {
            for (Section section : mSchedule.get(course).keySet()) {
                Class myClass = mSchedule.get(course).get(section);
                selectedClasses.add(myClass);
            }
        }

        return selectedClasses;
    }

    /** Return a Map of selected courses to a list of all classes for that course **/
    public Map<Course, ArrayList<Class>> getCoursesToClasses() {

        return mCoursesToClasses;
    }

    public void setClassesToCourses(Map<Course, ArrayList<Class>> coursesToClasses) {
        mCoursesToClasses = coursesToClasses;
    }

    /** Return a map representing the created schedule **/
    public Map<Course, Map<Section, Class>> getSchedule() {
        return mSchedule;
    }
}
