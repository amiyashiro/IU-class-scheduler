package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Adam on 3/28/2017.
 */

public class SelectedCourseListAdapter extends BaseAdapter {
    private Context mContext;
    private SelectedClasses mSelectedClasses;
    private ArrayList<Course> mSelectedCourses;
    private SeparatedClassListAdapter mSeparatedClassListAdapter;

    public SelectedCourseListAdapter(Context context, SelectedClasses selectedClasses, SeparatedClassListAdapter separatedClassListAdapter) {
        mContext = context;
        mSelectedClasses = selectedClasses;
        mSelectedCourses = mSelectedClasses.getmSelectedCourses();
        mSeparatedClassListAdapter = separatedClassListAdapter;
    }

    @Override
    public int getCount() { return mSelectedCourses.size(); }

    @Override
    public Object getItem(int position) { return mSelectedCourses.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Course course = mSelectedCourses.get(position);
        LayoutInflater layoutInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.course_list_item, null);

        TextView courseTextView = (TextView)convertView.findViewById(R.id.courseCode2TextView);
        courseTextView.setText(course.getmDepartment() + "-" + course.getmCode());

        TextView sectionTextView = (TextView)convertView.findViewById(R.id.courseSectionTextView);
        String sections = course.getmSections().toString();
        sectionTextView.setText(sections.substring(1, sections.length()-1));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeparatedClassListAdapter.setSeparatedList(course);
                mSeparatedClassListAdapter.notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
