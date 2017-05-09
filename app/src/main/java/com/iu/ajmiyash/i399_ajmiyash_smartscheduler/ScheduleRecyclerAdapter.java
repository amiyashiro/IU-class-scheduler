package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

/**
 * Created by Adam on 4/11/2017.
 */

public class ScheduleRecyclerAdapter extends RecyclerView.Adapter<ScheduleRecyclerAdapter.CardViewHolder> {
    private SeparatedClassListAdapter mSeparatedClassListAdapter;
    private SelectedClasses mSelectedClasses;
    private Map<Course, Map<Section, Class>> mSchedule;
    private Context mContext;


    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView courseTitle;
        RelativeLayout courseTitleLayout;
        LinearLayout classLinearLayout;
        View lectureEmpty;
        public CardViewHolder(View itemView) {
            super(itemView);
            courseTitle = (TextView)itemView.findViewById(R.id.schedCourseTitleTextView);
            //courseTitleLayout = (RelativeLayout)itemView.findViewById(R.id.courseTitleLayout);
            classLinearLayout = (LinearLayout)itemView.findViewById(R.id.classesLinearLayout);
        }
    }

    public ScheduleRecyclerAdapter(Context context, SelectedClasses selectedClasses) {
        mSelectedClasses = selectedClasses;
        mSchedule = mSelectedClasses.getSchedule();
        mContext = context;
    }

    public void setSeparatedClassListAdapter(SeparatedClassListAdapter separatedClassListAdapter) {
        mSeparatedClassListAdapter = separatedClassListAdapter;
    }

    @Override
    public CardViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_course_card, parent, false);
        View lectureEemptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_empty_class, null);

        CardViewHolder viewHolder = new CardViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        final Course course = mSelectedClasses.getmSelectedCourses().get(position);

        // set text of title
        holder.courseTitle.setText(course.getmDepartment() +
                "-" + course.getmCode() +
                " " + course.getmName() +
                " (" + course.getmCreditHours() + " CR)");

        // make title clickable
        holder.courseTitleLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(, "Clicked", Toast.LENGTH_LONG);
                mSeparatedClassListAdapter.setSeparatedList(course);
                mSeparatedClassListAdapter.notifyDataSetChanged();
            }
        });

        // set view for each section within course
        LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Set<Section> sections = mSchedule.get(course).keySet();
        Map<Section, Class> sectionToClass = mSchedule.get(course);
        if (sections.contains(Section.LECTURE)) {
            Class lecture = sectionToClass.get(Section.LECTURE);
            if (lecture == null) {
                // class not picked yet
                View emptyView = layoutInflater.inflate(R.layout.schedule_empty_class, null);

                TextView sectionTextView = (TextView)emptyView.findViewById(R.id.emptyClassSectionTextView);
                sectionTextView.setText(Section.LECTURE.toString());

                holder.classLinearLayout.addView(emptyView);
            } else {

            }
        }
        if (sections.contains(Section.LAB)) {
            Class lab = sectionToClass.get(Section.LAB);
            if (lab == null) {
                // class not picked yet
                View emptyView = layoutInflater.inflate(R.layout.schedule_empty_class, null);

                TextView sectionTextView = (TextView)emptyView.findViewById(R.id.emptyClassSectionTextView);
                sectionTextView.setText(Section.LAB.toString());

                holder.classLinearLayout.addView(emptyView);
            } else {

            }
        }
        if (sections.contains(Section.DISCUSSION)) {
            Class discussion = sectionToClass.get(Section.DISCUSSION);
            if (discussion == null) {
                // class not picked yet
                View emptyView = layoutInflater.inflate(R.layout.schedule_empty_class, null);

                TextView sectionTextView = (TextView)emptyView.findViewById(R.id.emptyClassSectionTextView);
                sectionTextView.setText(Section.DISCUSSION.toString());

                holder.classLinearLayout.addView(emptyView);
            } else {

            }
        }
        if (sections.contains(Section.RECITATION)) {
            Class recitation = sectionToClass.get(Section.RECITATION);
            if (recitation == null) {
                // class not picked yet
                View emptyView = layoutInflater.inflate(R.layout.schedule_empty_class, null);

                TextView sectionTextView = (TextView)emptyView.findViewById(R.id.emptyClassSectionTextView);
                sectionTextView.setText(Section.RECITATION.toString());

                holder.classLinearLayout.addView(emptyView);
            } else {

            }
        }
    }

    @Override
    public int getItemCount() {
        return mSelectedClasses.getSchedule().keySet().size();
    }
}
