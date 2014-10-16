package com.itpos.itposcheckin;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.itpos.itposcheckin.Fragments.TimeClock;
import com.itpos.itposcheckin.Fragments.ToDo;
import com.itpos.itposcheckin.NavigationDrawer.NavigationDrawerAdapter;
import com.itpos.itposcheckin.NavigationDrawer.NavigationDrawerItem;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private DrawerLayout myDrawerLayout;
    private ListView myListView;
    private ActionBarDrawerToggle myActionBarDrawerToggle;
    private CharSequence myDrawerTitle;
    private CharSequence myTitle;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavigationDrawerItem> navigationDrawerItems;
    private NavigationDrawerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#26a69a"));

        myTitle = myDrawerTitle = getTitle();

        //load the menu items
        navMenuTitles = getResources().getStringArray(R.array.navigation_drawer_items);

        //load the menu icons
        navMenuIcons = getResources().obtainTypedArray(R.array.navigation_drawer_icons);

        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        myListView = (ListView) findViewById(R.id.navigation_list);

        navigationDrawerItems = new ArrayList<NavigationDrawerItem>();

        for (int i=0; i<2; i++) {
            navigationDrawerItems.add(new NavigationDrawerItem(navMenuTitles[i],
                    navMenuIcons.getResourceId(i, -1)));
        }

        navMenuIcons.recycle();

        myListView.setOnItemClickListener(new SlideMenuClickListener());

        adapter = new NavigationDrawerAdapter(getApplicationContext(), navigationDrawerItems);
        myListView.setAdapter(adapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        myActionBarDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, //nav drawer open - description for accessibility
                R.string.app_name //nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(myTitle);
                //showing action bar icons
                invalidateOptionsMenu();
            }

            public void OnDrawerOpened(View drawerView) {
                getActionBar().setTitle(myDrawerTitle);
                //hide action bar icons
                invalidateOptionsMenu();
            }
        };
        myDrawerLayout.setDrawerListener(myActionBarDrawerToggle);

        if (savedInstanceState == null) {
            //on open start with restaurant fragment
            displayView(0);
        }
    }

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //display view for selected nav drawer item
            displayView(position);
        }
    }

    private void displayView(int position) {
        // update main content with the fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                //restaurant name fragment
                fragment = new TimeClock();
                break;
            case 1:
                //food items fragment
                fragment = new ToDo();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

            //update selected item and title, then close the drawer
            myListView.setItemChecked(position, true);
            myListView.setSelection(position);
            setTitle(navMenuTitles[position]);
            myDrawerLayout.closeDrawer(myListView);
        } else {
            //error creating fragment
            Log.e("MAIN ACTIVITY", "ERROR CREATING FRAGMENT");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        myTitle = title;
        getActionBar().setTitle(myTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        myActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        myActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
