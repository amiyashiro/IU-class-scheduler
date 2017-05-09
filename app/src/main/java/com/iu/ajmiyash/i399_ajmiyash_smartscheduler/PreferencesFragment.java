package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreferencesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PreferencesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreferencesFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private SelectedClasses mSelectedClasses;
    private ListView mSelectedCoursesListView;
    private ListView mSeparatedClassesListView;
    private ListView mScheduleListView;
    //private RecyclerView mScheduleRecycler;
    private SelectedCourseListAdapter mSelectedCourseListAdapter;
    private SeparatedClassListAdapter mSeparatedClassListAdapter;
    private ScheduleListAdapter mScheduleListAdapter;
    //private ScheduleRecyclerAdapter mScheduleRecyclerAdapter;
    //private DBHandler db;
    // main layout for fragment
    View mFragmentView;
    private DatabaseAccess db;

    public PreferencesFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PreferencesFragment.
     */
    public static PreferencesFragment newInstance() {
        PreferencesFragment fragment = new PreferencesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get DB instance
        db = DatabaseAccess.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preferences, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        //mSelectedCoursesListView = (ListView)v.findViewById(R.id.selectedCoursesListView);
        mSeparatedClassesListView = (ListView)v.findViewById(R.id.classesListView);
        mScheduleListView = (ListView)v.findViewById(R.id.scheduleListView);
        mFragmentView = v;

        onHiddenChanged(false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    // update layout when the fragment is shown again
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //do when hidden
        } else {
            //do when show

            // update selected classes
            mSelectedClasses = ((MainActivity)getActivity()).getSelectedClasses();
            ((TextView)mFragmentView.findViewById(R.id.courseIDTextView)).setText("Classes For ");

            // update map of courses to classes and schedule
            Map<Course, Map<Section, Class>> schedule = mSelectedClasses.getSchedule();
            Map<Course, ArrayList<Class>> classesToCourses = new HashMap<>();
            ArrayList<Course> courses = mSelectedClasses.getmSelectedCourses();
            for (Course course : courses) {
                ArrayList<Class> classes = db.findClasses(course);
                classesToCourses.put(course, classes);

                Set<Course> scheduledCourses = schedule.keySet();
                if (!scheduledCourses.contains(course)) {
                    schedule.put(course, null);
                }
            }
            mSelectedClasses.setClassesToCourses(classesToCourses);

            // find total credit hours
            double creditHours = 0.0;
            for (Course course : mSelectedClasses.getmSelectedCourses()) {
                creditHours += course.getmCreditHours();
            }
            // update total credit hours for schedule
            TextView creditHoursTextView = (TextView)getActivity().findViewById(R.id.creditHoursTextView);
            creditHoursTextView.setText("Credit Hours: " + creditHours);

            // update lists when fragment is shown
            // class list adapter
            if (mSeparatedClassListAdapter == null) {
                mSeparatedClassListAdapter = new SeparatedClassListAdapter(getActivity(), mSelectedClasses);
                mSeparatedClassesListView.setAdapter(mSeparatedClassListAdapter);
            } else {
                mSeparatedClassListAdapter.clearSeparatedList();
                mSeparatedClassListAdapter.notifyDataSetChanged();
            }


            // scheduled classes list adapter
            if (mScheduleListAdapter == null) {
                mScheduleListAdapter = new ScheduleListAdapter(getActivity(), mSelectedClasses, mSeparatedClassListAdapter);
                mScheduleListView.setAdapter(mScheduleListAdapter);

                // pass instance of scheduleAdapter to separatedClassListAdapter
                mSeparatedClassListAdapter.setScheduleListAdapter(mScheduleListAdapter);

                // pass main view to mScheduleAdapter
                mScheduleListAdapter.setFragmentView(mFragmentView);
            } else {
                mScheduleListAdapter.updateSchedule();
                mScheduleListAdapter.notifyDataSetChanged();
            }


        }

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
