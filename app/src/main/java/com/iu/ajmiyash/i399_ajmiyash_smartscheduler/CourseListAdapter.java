package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adam on 3/27/2017.
 */

public class CourseListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Course> mCourseList;
    private SelectedClasses mSelectedClasses;

    public CourseListAdapter(Context context, ArrayList<Course> searchResults, SelectedClasses selectedClasses) {
        mContext = context;
        mSelectedClasses = selectedClasses;
        mCourseList = searchResults;
    }

    public void updateSelectedClasses(SelectedClasses selectedClasses) {
        mSelectedClasses = selectedClasses;
    }

    @Override
    public int getCount() {
        return mCourseList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCourseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Course course = mCourseList.get(position);
        LayoutInflater layoutInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.list_item, null);

        TextView courseTextView = (TextView)convertView.findViewById(R.id.courseTitleTextView);
        courseTextView.setText(course.getmName() + " (" + course.getmCreditHours() + " CR)");

        TextView courseCodeTextView = (TextView)convertView.findViewById(R.id.courseCodeTextView);
        courseCodeTextView.setText((course.getmDepartment() + "-" + course.getmCode()));

        TextView sectionsTextView = (TextView)convertView.findViewById(R.id.sectionsTextView);
        String sections = course.getmSections().toString();
        sectionsTextView.setText(sections.substring(1, sections.length()-1));

        if (mSelectedClasses.getmSelectedCourses().contains(course)) {
            // show remove button
            RelativeLayout relativeLayout = (RelativeLayout)convertView.findViewById(R.id.courseSearchResultLayout);
            relativeLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorLimeStone20));

            ImageButton actionButton = (ImageButton)convertView.findViewById(R.id.actionButton);
            actionButton.setImageResource(R.drawable.ic_remove_course);
            actionButton.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // remove class
                    mSelectedClasses.getmSelectedCourses().remove(course);
                    mSelectedClasses.getSchedule().remove(course);
                    mSelectedClasses.getCoursesToClasses().remove(course);
                    CourseListAdapter.super.notifyDataSetChanged();
                }
            });
        } else {
            // show add button
            RelativeLayout relativeLayout = (RelativeLayout)convertView.findViewById(R.id.courseSearchResultLayout);
            relativeLayout.setBackgroundColor(Color.WHITE);

            ImageButton actionButton = (ImageButton)convertView.findViewById(R.id.actionButton);
            actionButton.setImageResource(R.drawable.ic_add_class);
            actionButton.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean flag = true;
                    for (Course schedCourse : mSelectedClasses.getmSelectedCourses()) {
                        if (schedCourse.getmCode().equals(course.getmCode())) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setMessage("The selected course has already been scheduled.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // user clicked button
                                            return;
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            flag = false;
                            dialog.show();
                            break;
                        }
                    }
                    if (flag) {
                        mSelectedClasses.getmSelectedCourses().add(course);
                        CourseListAdapter.super.notifyDataSetChanged();
                    }
                }
            });
        }



        return convertView;
    }
}
