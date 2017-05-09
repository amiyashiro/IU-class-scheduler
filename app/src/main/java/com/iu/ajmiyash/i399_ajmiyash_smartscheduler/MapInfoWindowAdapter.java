package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adam on 4/1/2017.
 */

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context mContext;
    // map of markers on map to list of classes at that location
    private Map<Marker, ArrayList<Class>> mMarkersToClasses;
    private ArrayList<Course> mSelectedCourses;

    public MapInfoWindowAdapter(Context context,
                                Map<Marker, ArrayList<Class>> markersToClasses,
                                ArrayList<Course> selectedCourses) {
        mContext = context;
        mMarkersToClasses = markersToClasses;
        mSelectedCourses = selectedCourses;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View infoWindowView = layoutInflater.inflate(R.layout.info_window_map, null);
        LinearLayout infoWindowLayout = (LinearLayout)infoWindowView.findViewById(R.id.mapInfoFrame);

        for (Class myClass : mMarkersToClasses.get(marker)) {
            // get course for this class
            /*
            ArrayList<Section> sections = new ArrayList<>();
            sections.add(Section.LECTURE);
            sections.add(Section.LAB);
            sections.add(Section.DISCUSSION);
            Course course1 = new Course(1, 1, "CSCI", "C211", "Comp Sci", 4, sections);
            */

            Course myCourse = null;
            for (Course course : mSelectedCourses) {
                if (myClass.getmCourseId() == course.getmCourseId()) {
                    myCourse = course;
                    break;
                }
            }

            if (myCourse == null) break;

            // set layout for class
            View layoutSection = layoutInflater.inflate(R.layout.map_info_section, null);

            TextView classTitle = (TextView) layoutSection.findViewById(R.id.mapClassTitleText);
            classTitle.setText(myCourse.getmDepartment() + "-" + myCourse.getmCode());

            TextView professorTextView = (TextView) layoutSection.findViewById(R.id.professorTextView);
            professorTextView.setText(myClass.getmProfessor());

            TextView buildingTextView = (TextView) layoutSection.findViewById(R.id.buildingTextView);
            buildingTextView.setText(myClass.getmBuilding() + String.format("%03d", myClass.getmRoom()));

            TextView timeTextView = (TextView)layoutSection.findViewById(R.id.timeTextView);
            timeTextView.setText(myClass.getmStartTime().toString(true) + "-" + myClass.getmEndTime().toString(true));

            // add layout to popup
            infoWindowLayout.addView(layoutSection);

        }

        return infoWindowView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
