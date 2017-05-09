package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private TabLayout mPeriodTabs;
    private DatabaseAccess db;
    private MapInfoWindowAdapter mMapInfoWindowAdapter;

    private SelectedClasses mSelectedClasses;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
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
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        mSelectedClasses = ((MainActivity)getActivity()).getSelectedClasses();
        mPeriodTabs = (TabLayout)v.findViewById(R.id.periodTabsMap);

        // handle tabs change in selected tab
        mPeriodTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Toast.makeText(getActivity(), "new tab", Toast.LENGTH_LONG).show();
                mGoogleMap.clear();
                onMapReady(mGoogleMap);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        mMapView = (MapView)v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);

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

        //db = DatabaseAccess.getInstance(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mGoogleMap = map;

        // Center camera on IU campus
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.171672, -86.516792), 14));

        Map<Building, ArrayList<Class>> buildingsToClasses = getBuildings(mSelectedClasses.getSelectedClasses());

        // add markers to map
        Map<Marker, ArrayList<Class>> markersToClasses = new HashMap<>();
        for (Building building : buildingsToClasses.keySet()) {
            // make marker from building
            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(building.getLatLng()));

            markersToClasses.put(marker, buildingsToClasses.get(building));
        }

        mMapInfoWindowAdapter = new MapInfoWindowAdapter(getActivity(),
                markersToClasses,
                mSelectedClasses.getmSelectedCourses());
        mGoogleMap.setInfoWindowAdapter(mMapInfoWindowAdapter);

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
            //update selectedClasses
            mSelectedClasses = ((MainActivity)getActivity()).getSelectedClasses();

            // update map
            if (mGoogleMap != null) {
                mGoogleMap.clear();
                onMapReady(mGoogleMap);
            }
        }
    }


    /** Returns a mapping of building objects to a list of classes in that building
     *  Gets info about each building from DB
     */
    private Map<Building, ArrayList<Class>> getBuildings(ArrayList<Class> classes) {
        HashMap<Building, ArrayList<Class>> buildingsToClasses = new HashMap<>();

        int period = mPeriodTabs.getSelectedTabPosition();

        for (Class myClass : classes) {
            Building building;
            if (myClass != null) {
                building = db.findBuilding(myClass.getmBuilding());
            } else {
                continue;
            }

            // skip showing building if the class period and selected tab do not line up
            if ( (period == 0 && myClass.getmPeriod() == Period.SECOND_EIGHT_WEEKS) ||
                    (period == 1 && myClass.getmPeriod() == Period.FIRST_EIGHT_WEEKS)) {
                continue;
            }

            boolean flag = true;
            for (Building thisBuilding : buildingsToClasses.keySet()) {
                if (building.getmName().equals(thisBuilding.getmName())) {
                    buildingsToClasses.get(thisBuilding).add(myClass);
                    flag = false;
                    break;
                }
            }

            if (flag) {
                ArrayList<Class> classList = new ArrayList<>();
                classList.add(myClass);
                buildingsToClasses.put(building, classList);
            }
        }

        return buildingsToClasses;
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
