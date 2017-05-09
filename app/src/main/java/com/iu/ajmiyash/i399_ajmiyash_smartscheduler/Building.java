package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Adam on 4/1/2017.
 */

public class Building {

    private String mName;
    private double mLatitude;
    private double mLongitude;

    public Building(String name, double latitude, double longitude) {
        mName = name;
        mLongitude = longitude;
        mLatitude = latitude;
    }

    public LatLng getLatLng() {
        return new LatLng(mLatitude, mLongitude);
    }

    public String getmName() {
        return mName;
    }
}
