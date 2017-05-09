package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Adam on 4/10/2017.
 */

public class ScheduleListAdapter extends BaseAdapter {
    private Context mContext;
    private SelectedClasses mSelectedClasses;
    private SeparatedClassListAdapter mSeparatedClassListAdapter;
    private View mParentView;

    // Maps selected courses to a map of each section in that course to the selected class for that section
    private Map<Course, Map<Section, Class>> mSchedule;

    public ScheduleListAdapter(Context context, SelectedClasses selectedClasses, SeparatedClassListAdapter separatedClassListAdapter) {
        mContext = context;
        mSelectedClasses = selectedClasses;
        mSchedule = mSelectedClasses.getSchedule();
        mSeparatedClassListAdapter = separatedClassListAdapter;

        initializeSchedule();
    }

    private void initializeSchedule() {
        ArrayList<Course> courses = mSelectedClasses.getmSelectedCourses();

        for (Course course : courses) {
            if (mSchedule.get(course) == null) {
                HashMap<Section, Class> sectionToClass = new HashMap<>();
                for (Section section : course.getmSections()) {
                    sectionToClass.put(section, null);
                }
                mSchedule.put(course, sectionToClass);
            }
        }
    }

    public void setSeparatedClassListAdapter(SeparatedClassListAdapter separatedClassListAdapter) {
        mSeparatedClassListAdapter = separatedClassListAdapter;
    }

    public void updateSchedule() {
        mSchedule = mSelectedClasses.getSchedule();
        initializeSchedule();
    }

    @Override
    public int getCount() {
        return mSchedule.keySet().size();
    }

    @Override
    public Object getItem(int position) {
        return mSelectedClasses.getmSelectedCourses();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Course course = mSelectedClasses.getmSelectedCourses().get(position);
        LayoutInflater layoutInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View convertView = layoutInflater.inflate(R.layout.schedule_course_card, null);

        TextView courseTitle = (TextView)convertView.findViewById(R.id.schedCourseTitleTextView);
        courseTitle.setText(course.getmDepartment() +
                "-" + course.getmCode() +
                " " + course.getmName() +
                " (" + course.getmCreditHours() + " CR)");

        // Make course title clickable
        RelativeLayout button = (RelativeLayout)convertView.findViewById(R.id.classTitleLayout);
        button.setOnClickListener(new RelativeLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "Card clicked", Toast.LENGTH_LONG).show();
                mSeparatedClassListAdapter.setSeparatedList(course);

                ((TextView)mParentView.findViewById(R.id.courseIDTextView))
                        .setText("Classes For " + course.getmDepartment() + "-" + course.getmCode());

                mSeparatedClassListAdapter.notifyDataSetChanged();
            }
        });

        // remove course
        ImageButton remove = (ImageButton)convertView.findViewById(R.id.removeCourseButton);
        remove.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ask for confirmation to remove course
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to remove this course?")
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // remove course
                                mSchedule.remove(course);
                                mSelectedClasses.getmSelectedCourses().remove(course);
                                if (mSeparatedClassListAdapter.getCurrentCourse() == course) {
                                    mSeparatedClassListAdapter.setSeparatedList(null);

                                    ((TextView)mParentView.findViewById(R.id.courseIDTextView))
                                            .setText("Classes For ");
                                }

                                // find total credit hours
                                double creditHours = 0.0;
                                for (Course myCourse : mSelectedClasses.getmSelectedCourses()) {
                                    creditHours += myCourse.getmCreditHours();
                                }
                                // update total credit hours for schedule
                                TextView creditHoursTextView = (TextView)mParentView.findViewById(R.id.creditHoursTextView);
                                creditHoursTextView.setText("Credit Hours: " + creditHours);

                                ScheduleListAdapter.this.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // keep course, do nothing
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        // linearLayout for course card (place classes in the linear layout)
        LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.classesLinearLayout);



        // set view for each section within course
        Map<Section, Class> sectionToClass = mSchedule.get(course);
        Set<Section> sections = sectionToClass.keySet();


        // iterate through sections
        // have to sort set of sections??
        for (Section section : sections) {
            final Class myClass = sectionToClass.get(section);
            if (myClass == null) {
                // class for this section is not scheduled yet
                View emptyView = layoutInflater.inflate(R.layout.schedule_empty_class, null);

                TextView sectionTextView = (TextView)emptyView.findViewById(R.id.emptyClassSectionTextView);
                sectionTextView.setText("Pick a " + section.toString());

                linearLayout.addView(emptyView);
            } else {
                // show info for scheduled class
                View scheduledClassView = layoutInflater.inflate(R.layout.class_in_card, null);

                TextView sectionTextView = (TextView)scheduledClassView.findViewById(R.id.schedClassSectionTextView);
                sectionTextView.setText(myClass.getmSection().toString());

                TextView professorTextView = (TextView)scheduledClassView.findViewById(R.id.schedClassProfessorTextView);
                professorTextView.setText(myClass.getmProfessor());

                TextView timeTextView = (TextView)scheduledClassView.findViewById(R.id.schedTimeTextView);
                timeTextView.setText(myClass.getmStartTime().toString(true) + "-" + myClass.getmEndTime().toString(true));

                TextView locationTextView = (TextView)scheduledClassView.findViewById(R.id.schedLocationTextView);
                locationTextView.setText(myClass.getmBuilding() + String.format("%03d", myClass.getmRoom()));

                TextView daysTextView = (TextView)scheduledClassView.findViewById(R.id.schedDaysTextView);
                daysTextView.setText(Day.toShortString(myClass.getmDays()));

                TextView topicTextView = (TextView)scheduledClassView.findViewById(R.id.schedTopicTextView);
                topicTextView.setText(myClass.getmTopic());

                TextView periodTextView = (TextView)scheduledClassView.findViewById(R.id.schedPeriodTextView);
                periodTextView.setText(myClass.getmPeriod().toString());

                Button removeClass = (Button)scheduledClassView.findViewById(R.id.removeClassButton);
                removeClass.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(mContext, "remove class", Toast.LENGTH_LONG);
                        mSelectedClasses.getSchedule().get(course).put(myClass.getmSection(), null);
                        ScheduleListAdapter.super.notifyDataSetChanged();
                        mSeparatedClassListAdapter.setSeparatedList(course);
                    }
                });

                linearLayout.addView(scheduledClassView);
            }
        }


        return convertView;
    }

    public void setFragmentView(View v) {
        mParentView = v;
    }
}
