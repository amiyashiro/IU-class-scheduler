package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * Created by Adam on 4/4/2017.
 */

public class SeparatedClassListAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    private Context mContext;
    private SelectedClasses mSelectedClasses;
    private ScheduleListAdapter mScheduleListAdapter;
    // instance of coursesToClasses from mSelectedClasses
    private Map<Course, ArrayList<Class>> mCoursesToClasses;
    // The course whose classes are currently shown
    private Course mCurrentCourse;
    // ArrayList of Sections and Classes (represents all classes for a single course)
    private ArrayList<Object> mSeparatedList;

    public SeparatedClassListAdapter(Context context, SelectedClasses selectedClasses) {
        mContext = context;
        mSelectedClasses = selectedClasses;
    }

    public void setScheduleListAdapter(ScheduleListAdapter scheduleListAdapter) {
        mScheduleListAdapter = scheduleListAdapter;
    }

    public Course getCurrentCourse() {
        return mCurrentCourse;
    }

    /** Sets the separatedList to the List of classes for the given course **/
    public void setSeparatedList(Course course) {
        // start with empty list
        mSeparatedList = new ArrayList<>();

        mCurrentCourse = course;
        if (mCurrentCourse == null) {
            return;
        }

        mCoursesToClasses = mSelectedClasses.getCoursesToClasses();
        ArrayList<Class> classes = mCoursesToClasses.get(mCurrentCourse);

        Map<Section, Class> scheduledClasses = mSelectedClasses.getSchedule().get(mCurrentCourse);

        // find an already scheduled class to only display related classes
        int relation = 0;
        for (Section section : mCurrentCourse.getmSections()) {
            if (scheduledClasses.get(section) != null) {
                relation = scheduledClasses.get(section).getmRelation();
            }
        }

        // iterate through each section in the course, adding each class in that section
        for (Section section : mCurrentCourse.getmSections()) {
            if (scheduledClasses.get(section) == null) {
                mSeparatedList.add(section);
                for (Class myClass : classes) {
                    if (myClass.getmSection() == section &&
                            (myClass.getmRelation() == relation ||
                            relation == 0)) {
                        mSeparatedList.add(myClass);
                    }
                }
            }
        }
    }

    public void clearSeparatedList() {
        mSeparatedList = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mSeparatedList == null) {
            return null;
        } else {
            // return view
            if (getItemViewType(position) == 1) {
                // show header
                Section section = (Section)mSeparatedList.get(position);
                LayoutInflater layoutInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.sep_class_list_section, null);

                TextView sectionHeader = (TextView)convertView.findViewById(R.id.sectionTextView);
                sectionHeader.setText(section.toString());

                return convertView;
            } else {
                // show class
                final Class myClass = (Class)mSeparatedList.get(position);
                LayoutInflater layoutInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.sep_class_list_item, null);

                TextView profTextView = (TextView)convertView.findViewById(R.id.classProfessorTextView);
                profTextView.setText(myClass.getmProfessor());

                TextView daysTextView = (TextView)convertView.findViewById(R.id.classDaysTextView);
                daysTextView.setText(Day.toShortString(myClass.getmDays()));

                TextView timeTextView = (TextView)convertView.findViewById(R.id.classTimeTextView);
                timeTextView.setText(myClass.getmStartTime().toString(true) + '-' + myClass.getmEndTime().toString(true));

                TextView periodTextView = (TextView)convertView.findViewById(R.id.classPeriodTextView);
                periodTextView.setText(myClass.getmPeriod().toString());

                TextView buildingTextView = (TextView)convertView.findViewById(R.id.classBuildingTextView);
                buildingTextView.setText(myClass.getmBuilding() + String.format("%03d", myClass.getmRoom()));

                TextView topicTextView = (TextView)convertView.findViewById(R.id.classTopicTextView);
                topicTextView.setText(myClass.getmTopic());

                // add class on click
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // check for conflicts with other classes
                        boolean flag = true;
                        for (Course course : mSelectedClasses.getSchedule().keySet()) {
                            for (Section section : course.getmSections()) {
                                Class thisClass = mSelectedClasses.getSchedule().get(course).get(section);
                                if (thisClass != null) {
                                    // a class is scheduled, check for conflict
                                    for (Day day : myClass.getmDays()) {
                                        if (thisClass.getmDays().contains(day)) {
                                            // classes are on same day, check time
                                            if (myClass.getmEndTime().toValue() > thisClass.getmStartTime().toValue() &&
                                                    myClass.getmStartTime().toValue() < thisClass.getmEndTime().toValue()) {
                                                // classes conflict, show message
                                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                                builder.setMessage("The selected class conflicts with an already scheduled class.")
                                                        .setTitle("Time Conflict")
                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                // user clicked button
                                                            }
                                                        });
                                                AlertDialog dialog = builder.create();
                                                flag = false;
                                                dialog.show();
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (flag == true) {
                            // all classes have been checked, add class
                            mSelectedClasses.getSchedule().get(mCurrentCourse).put(myClass.getmSection(), myClass);
                            mScheduleListAdapter.notifyDataSetChanged();
                            setSeparatedList(mCurrentCourse);
                            SeparatedClassListAdapter.super.notifyDataSetChanged();
                        }
                    }
                });

                return convertView;
            }
        }
    }

    @Override
    public Object getItem(int position) {
        return mSeparatedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        if (mSeparatedList == null) {
            return 0;
        } else {
            return mSeparatedList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Section) {
            // Section header == 1
            return 1;
        } else {
            // class == 2
            return 2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }
}
