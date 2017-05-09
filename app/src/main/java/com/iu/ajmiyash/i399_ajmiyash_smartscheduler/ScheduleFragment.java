package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private WeekView mWeekView;
    private TabLayout mPeriodTabs;
    private SelectedClasses mSelectedClasses;
    private MonthLoader.MonthChangeListener mMonthChangeListener;

    private OnFragmentInteractionListener mListener;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ScheduleFragment.
     */
    public static ScheduleFragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mWeekView = (WeekView)view.findViewById(R.id.weekView);
        mPeriodTabs = (TabLayout)view.findViewById(R.id.periodTabs);

        mSelectedClasses = ((MainActivity)getActivity()).getSelectedClasses();

        // must implement monthChangeListener
        // return an empty list, scroll has been disabled so we do not need to update events
        mMonthChangeListener = new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                // Populate the week view with current schedule
                List<WeekViewEvent> events = new ArrayList<>();
                Map<Course, Map<Section, Class>> schedule = mSelectedClasses.getSchedule();

                // set array of colors
                int color = 0;
                int[] colors = {0xfff1be48, 0xff008264, 0xff006298, 0xff66435a, 0xff4a3c31, 0xff191919, 0xffdd0031, 0xff83786f};

                // check if first or second period is selected
                int period = mPeriodTabs.getSelectedTabPosition();

                if (newMonth < Calendar.getInstance().get(Calendar.MONTH) || newMonth > Calendar.getInstance().get(Calendar.MONTH)) {
                    return events;
                }

                mWeekView.setTodayBackgroundColor(0x05000000);
                if (period == 0) {
                    // First eight weeks
                    int id = 1;
                    for (Course course : schedule.keySet()) {
                        for (Section section : schedule.get(course).keySet()) {
                            Class myClass = schedule.get(course).get(section);
                            if (myClass == null) continue;
                            if (myClass.getmPeriod() == Period.FIRST_EIGHT_WEEKS || myClass.getmPeriod() == Period.FULL_TERM) {
                                // show class
                                for (Day dayOfWeek : myClass.getmDays()) {
                                    Calendar startTime = Calendar.getInstance();
                                    int day = 0;
                                    int startDay = startTime.get(Calendar.DAY_OF_YEAR);
                                    switch (dayOfWeek) {
                                        case MONDAY:
                                            day = startDay + 1;
                                            break;
                                        case TUESDAY:
                                            day = startDay + 2;
                                            break;
                                        case WEDNESDAY:
                                            day = startDay + 3;
                                            break;
                                        case THURSDAY:
                                            day = startDay + 4;
                                            break;
                                        case FRIDAY:
                                            day = startDay + 5;
                                            break;
                                    }
                                    startTime.set(Calendar.DAY_OF_YEAR, day);
                                    startTime.set(Calendar.HOUR_OF_DAY, myClass.getmStartTime().getHour());
                                    startTime.set(Calendar.MINUTE, myClass.getmStartTime().getMinute());
                                    Calendar endTime = (Calendar) startTime.clone();
                                    endTime.set(Calendar.HOUR_OF_DAY, myClass.getmEndTime().getHour());
                                    endTime.set(Calendar.MINUTE, myClass.getmEndTime().getMinute());
                                    String name = section.toString() + "\n\n" + course.getmDepartment() + "-" +
                                            course.getmCode() + "\n" +
                                            myClass.getmStartTime().toString(true) + "-" +
                                            myClass.getmEndTime().toString(true) + "\n\n";
                                    WeekViewEvent event = new WeekViewEvent(id++, name, startTime, endTime);
                                    event.setLocation(myClass.getmBuilding() + String.format("%03d", myClass.getmRoom()));
                                    event.setColor(colors[color]);
                                    events.add(event);
                                }

                            }
                        }
                        color++;
                    }

                } else {
                    // second eight weeks
                    int id = 1;
                    for (Course course : schedule.keySet()) {
                        for (Section section : schedule.get(course).keySet()) {
                            Class myClass = schedule.get(course).get(section);
                            if (myClass == null) continue;
                            if (myClass.getmPeriod() == Period.SECOND_EIGHT_WEEKS || myClass.getmPeriod() == Period.FULL_TERM) {
                                // show class
                                for (Day dayOfWeek : myClass.getmDays()) {
                                    Calendar startTime = Calendar.getInstance();
                                    int day = 0;
                                    int startDay = startTime.get(Calendar.DAY_OF_YEAR);
                                    switch (dayOfWeek) {
                                        case MONDAY:
                                            day = startDay + 1;
                                            break;
                                        case TUESDAY:
                                            day = startDay + 2;
                                            break;
                                        case WEDNESDAY:
                                            day = startDay + 3;
                                            break;
                                        case THURSDAY:
                                            day = startDay + 4;
                                            break;
                                        case FRIDAY:
                                            day = startDay + 5;
                                            break;
                                    }
                                    startTime.set(Calendar.DAY_OF_YEAR, day);
                                    startTime.set(Calendar.HOUR_OF_DAY, myClass.getmStartTime().getHour());
                                    startTime.set(Calendar.MINUTE, myClass.getmStartTime().getMinute());
                                    Calendar endTime = (Calendar) startTime.clone();
                                    endTime.set(Calendar.HOUR_OF_DAY, myClass.getmEndTime().getHour());
                                    endTime.set(Calendar.MINUTE, myClass.getmEndTime().getMinute());
                                    String name = section.toString() + "\n\n" + course.getmDepartment() + "-" +
                                            course.getmCode() + "\n" +
                                            myClass.getmStartTime().toString(true) + "-" +
                                            myClass.getmEndTime().toString(true) + "\n\n";
                                    WeekViewEvent event = new WeekViewEvent(id++, name, startTime, endTime);
                                    event.setLocation(myClass.getmBuilding() + String.format("%03d", myClass.getmRoom()));
                                    event.setColor(colors[color]);
                                    events.add(event);
                                }
                            }
                        }
                        color++;
                    }
                }
                return events;
            }
        };

        // attach MonthChangeListener to WeekView
        mWeekView.setMonthChangeListener(mMonthChangeListener);
        mWeekView.goToHour(8);

        // handle tabs change in selected tab
        mPeriodTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Toast.makeText(getActivity(), "new tab", Toast.LENGTH_LONG).show();
                mMonthChangeListener.onMonthChange(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH));
                mWeekView.notifyDatasetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        onHiddenChanged(false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            // do when hidden
            ((MainActivity)getActivity()).getSupportActionBar().show();
        } else {
            // do when show
            ((MainActivity)getActivity()).getSupportActionBar().hide();

            // update selected classes
            mSelectedClasses = ((MainActivity)getActivity()).getSelectedClasses();
            mMonthChangeListener.onMonthChange(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH));
            mWeekView.notifyDatasetChanged();
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
