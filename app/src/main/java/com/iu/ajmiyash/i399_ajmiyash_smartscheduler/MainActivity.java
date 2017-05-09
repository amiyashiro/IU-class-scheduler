package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import android.net.Uri;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
    implements SearchFragment.OnFragmentInteractionListener, PreferencesFragment.OnFragmentInteractionListener, ScheduleFragment.OnFragmentInteractionListener, MapFragment.OnFragmentInteractionListener{

    private static final String SELECTED_ITEM = "selected_item";
    private BottomNavigationView mBottomBar;
    private int mSelectedItem;

    private SelectedClasses mSelectedClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // listen for clicks on the bottom bar
        mBottomBar = (BottomNavigationView)findViewById(R.id.navigation);
        mBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectFragment(item);
                return true;
            }
        });

        mSelectedClasses = new SelectedClasses();

        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = mBottomBar.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = mBottomBar.getMenu().getItem(0);
        }

        selectFragment(selectedItem);
    }

    private void selectFragment(MenuItem item) {
        Fragment fragment = null;
        String tag = "";

        FragmentManager fm = getFragmentManager();

        switch (item.getItemId()) {
            case R.id.menu_search:
                if (getVisibleFragment() != null && getVisibleFragment().getTag() != "search") {
                    fm.beginTransaction().hide(fm.findFragmentByTag(getVisibleFragment().getTag())).commit();
                }
                if (fm.findFragmentByTag("search") != null) {
                    fm.beginTransaction().show(fm.findFragmentByTag("search")).commit();
                } else {
                    fm.beginTransaction().add(R.id.container, SearchFragment.newInstance(), "search").commit();
                }
                break;
            case R.id.menu_preferences:
                if (getVisibleFragment() != null && getVisibleFragment().getTag() != "preferences") {
                    fm.beginTransaction().hide(fm.findFragmentByTag(getVisibleFragment().getTag())).commit();
                }
                if (fm.findFragmentByTag("preferences") != null) {
                    fm.beginTransaction().show(fm.findFragmentByTag("preferences")).commit();
                } else {
                    fm.beginTransaction().add(R.id.container, PreferencesFragment.newInstance(), "preferences").commit();
                }
                break;
            case R.id.menu_schedule:
                if (getVisibleFragment() != null && getVisibleFragment().getTag() != "schedule") {
                    fm.beginTransaction().hide(fm.findFragmentByTag(getVisibleFragment().getTag())).commit();
                }
                if (fm.findFragmentByTag("schedule") != null) {
                    fm.beginTransaction().show(fm.findFragmentByTag("schedule")).commit();
                } else {
                    fm.beginTransaction().add(R.id.container, ScheduleFragment.newInstance(), "schedule").commit();
                }
                break;
            case R.id.menu_map:
                if (getVisibleFragment() != null && getVisibleFragment().getTag() != "map") {
                    fm.beginTransaction().hide(fm.findFragmentByTag(getVisibleFragment().getTag())).commit();
                }
                if (fm.findFragmentByTag("map") != null) {
                    fm.beginTransaction().show(fm.findFragmentByTag("map")).commit();
                } else {
                    fm.beginTransaction().add(R.id.container, MapFragment.newInstance(), "map").commit();
                }
                break;
        }

        mSelectedItem = item.getItemId();
        updateToolbarText(item.getTitle());
    }

    private Fragment getVisibleFragment() {
        FragmentManager fm = this.getFragmentManager();
        Fragment fragment;
        String[] frags = {"search", "preferences", "schedule", "map"};

        for (String frag : frags) {
            fragment = fm.findFragmentByTag(frag);
            if (fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }

        return null;
    }

    public void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(text);
            actionBar.setIcon(R.mipmap.ic_trident_white);
        }
    }

    /** Handle click for search (Search Fragment) **/
    public void onSearchClick(View v) {
        SearchFragment fragment = (SearchFragment) getFragmentManager().findFragmentByTag("search");
        fragment.performSearch();
    }

    /** Return the instance of SelectedClasses (called from fragments) **/
    public SelectedClasses getSelectedClasses() {
        return mSelectedClasses;
    }


    // must implement onFragmentInteraction for each fragment
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
