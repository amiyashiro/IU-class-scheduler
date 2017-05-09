package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.WrapperListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;

    // Elements in fragment_search.xml
    private EditText mSearchEditText;
    private Spinner mTermSpinner;
    private ListView mCourseListView;
    // DBHelper
    //private DBHandler db;
    private DatabaseAccess db;
    // class to hold selected classes
    private SelectedClasses mSelectedClasses;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_TEXT, text);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (getArguments() != null) {
            //mText = getArguments().getString(ARG_TEXT);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
        */

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // hide keyboard
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mSearchEditText = (EditText)view.findViewById(R.id.searchEditText);
        mTermSpinner = (Spinner)view.findViewById(R.id.termSpinner);
        mCourseListView = (ListView)view.findViewById(R.id.courseListView);
        mSelectedClasses = ((MainActivity)getActivity()).getSelectedClasses();

        db = DatabaseAccess.getInstance(getActivity());

        onHiddenChanged(false);
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

        db = DatabaseAccess.getInstance(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /** update layout when the fragment is shown again **/
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //do when hidden
        } else {
            //do when show

            // update listAdapter
            if (mCourseListView.getAdapter() != null) {
                mSelectedClasses = ((MainActivity)getActivity()).getSelectedClasses();
                ((CourseListAdapter)mCourseListView.getAdapter()).updateSelectedClasses(mSelectedClasses);
                ((CourseListAdapter)mCourseListView.getAdapter()).notifyDataSetChanged();
            }
        }
    }


    /** Searches the DB with input in searchEditText and populates ExpandableListView **/
    public void performSearch() {
        //Toast.makeText(getActivity(), "Search Frag", Toast.LENGTH_LONG).show();

        // get query from the search bar
        String query = mSearchEditText.getText().toString();

        // get selected term from spinner
        String strTerm = mTermSpinner.getSelectedItem().toString();
        Term term = null;
        switch (strTerm.substring(0, 2)) {
            // SPRING
            case "SP":
                term = db.findTerm(Integer.parseInt(strTerm.substring(2, 6)), Season.SPRING);
                break;

            // FALL
            case "FA":
                term = db.findTerm(Integer.parseInt(strTerm.substring(2, 6)), Season.FALL);
                break;

            // SUMMER
            case "SU":
                term = db.findTerm(Integer.parseInt(strTerm.substring(2, 6)), Season.SUMMER);
                break;
        }

        // find courses that match query and term
        ArrayList<Course> courses = getCourses(term, query.toUpperCase());
        // find all classes associated with each course, store in HashMap

        // populate listView with search results
        showSearchResults(courses);
    }

    /** Find all courses that belong to a given term **/
    private ArrayList<Course> getCourses(Term term, String query) {
        ArrayList<Course> courses;

        // call DBHelper findCourses
        courses = db.findCourses(term, query);

        return courses;
    }

    /** Find all classes that belong to a given course **/
    private ArrayList<Class> getClasses(Course course) {
        ArrayList<Class> classes;

        // call DBHelper findClasses
        classes = db.findClasses(course);

        return classes;
    }

    /** Populate the ExpandableListView with search results **/
    private void showSearchResults(ArrayList<Course> searchResults) {
        // setup list adapter to show courses
        CourseListAdapter courseListAdapter = new CourseListAdapter(getActivity(), searchResults, mSelectedClasses);
        mCourseListView.setAdapter(courseListAdapter);
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
