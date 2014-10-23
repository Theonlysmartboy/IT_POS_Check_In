package com.itpos.itposcheckin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.itpos.itposcheckin.Fragments.DefaultFragment;
import com.itpos.itposcheckin.Fragments.TimeClock;
import com.itpos.itposcheckin.Fragments.ToDo;
import com.itpos.itposcheckin.NavigationDrawer.NavigationDrawerAdapter;
import com.itpos.itposcheckin.NavigationDrawer.NavigationDrawerItem;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends DrawerLayoutActivity {
    private static final String TAG_ACTIVE_FRAGMENT = "fragment_active";

    // constants that represent the fragments
    public static final int TIME_CLOCK = 0;
    public static final int TO_DO = 1;
    private DefaultFragment activeFragment = null;

    // more nav drawer stuff
    private NavigationDrawerAdapter mNavDrawerAdapter;
    private ArrayList<NavigationDrawerItem> navigationDrawerItems;
    private String[] navMenuTitles;
    private HashMap<Integer, String> fragmentTitles;
    private Bundle currentBundle;

    @Override
    public void init() {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#26a69a"));

        // retrieve array from XML
        TypedArray navigationIcons = getResources().obtainTypedArray(R.array.navigation_drawer_icons);
        navMenuTitles = getResources().getStringArray(R.array.navigation_drawer_items);
        navigationDrawerItems = new ArrayList<NavigationDrawerItem>();

        // should add items to the ArrayList of NavigationDrawerItems4
        for(int i = 0; i < navMenuTitles.length; i++) {
            // populate the navigation drawer array
            navigationDrawerItems.add(new NavigationDrawerItem(navMenuTitles[i], navigationIcons.getDrawable(i)));
        }
        // recycle the typed array when done with it
        navigationIcons.recycle();

        mNavDrawerAdapter = new NavigationDrawerAdapter(this, navigationDrawerItems);

        // we need a HashMap to map the Titles of a fragment that are outside the nav drawer
        //fragmentTitles = new HashMap<Integer, String>();
        //ex:
        //fragmentTitles.put(NEW_FRAGMENT, getString(R.string.string_id));
    }

    @Override
    public void restoreFragment(Bundle savedInstanceState) {
        //restore instance of the fragment
        activeFragment = (DefaultFragment) getFragmentManager().getFragment(savedInstanceState, "activeFragment");
    }

    @Override
    public void displayView(int position, Bundle fragmentBundle) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (position) {
            case TIME_CLOCK:
                activeFragment = new TimeClock();
                clearBackStack();
                break;
            case TO_DO:
                activeFragment = new ToDo();
                fragmentTransaction.addToBackStack(null);
                break;
            default:
                break;
        }
    }

    @Override
    public String getLogTag() { return "MainActivity"; }

    @Override
    protected BaseAdapter getAdapter() { return mNavDrawerAdapter; }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "activeFragment", activeFragment);
    }
}
