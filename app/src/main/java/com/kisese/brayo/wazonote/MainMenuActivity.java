package com.kisese.brayo.wazonote;

import android.app.Activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import storage.AndroidExplorer;
import storage.MainActivityList;

/**
 * Created by Brayo on 1/6/2015.
 */
public class MainMenuActivity extends ActionBarActivity {

    String[] menutitles;
    TypedArray menuIcons;
    // nav ic_drawer title
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<RowItem> rowItems;
    AlertDialog myDialog;
    View alertView;
    private CustomAdapter adapter;


    private SharedPreferences notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.menu_activity);

        mTitle = mDrawerTitle = Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name)+ "</font");
        menutitles = getResources().getStringArray(R.array.titles);
        menuIcons = getResources().obtainTypedArray(R.array.icons);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.slider_list);

        rowItems = new ArrayList<RowItem>();

        for (int i = 0; i < menutitles.length; i++) {
            RowItem items = new RowItem(menutitles[i], menuIcons.getResourceId(      i, -1));
            rowItems.add(items);
        }

        menuIcons.recycle();
        adapter = new CustomAdapter(getApplicationContext(), rowItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new SlideitemListener());

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F08080")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name)+ "</font"));
        //getSupportActionBar().getThemedContext();
        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name,R.string.app_name)
        {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                supportInvalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            updateDisplay(0);
        }
    }  class SlideitemListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {                  updateDisplay(position);
        }

    }  private void updateDisplay(int position) {
        Fragment fragment = null;
        Activity act = null;
        switch (position) {
            case 0:
                fragment = new MainActivityList();
                break;


            case 1:
                Intent addNote = new Intent(this, AddNoteActivity.class);
                this.startActivity(addNote);
                break;

            case 2:
                Intent addDoodle = new Intent(this, Doodlz.class);
                this.startActivity(addDoodle);
                break;

            case 3:
                Intent myDoodles = new Intent(this, MyDoodlesActivity.class);
                this.startActivity(myDoodles);
                break;

            case 4:
                Intent stickyNote = new Intent(this, StickyNoteActivity.class);
                this.startActivity(stickyNote);
                break;

            case 5:
                Intent secureNote = new Intent(this, SecureActivity.class);
                this.startActivity(secureNote);
                break;

            case 6:
                Intent backups = new Intent(this, AndroidExplorer.class);
                this.startActivity(backups);
                break;

            case 7:
                startHelpDialog();
                        break;
            case 8:
                startAboutDialog();
                break;

            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
          //  FragmentManager fragmentManager = getFragmentManager();
           // fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            // update selected item and title, then close the ic_drawer
           // setTitle(menutitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
       // getSupportActionBar().setTitle(mTitle);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav ic_drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {

            case R.id.action_add_new:
                Intent newNote = new Intent(this, AddNoteActivity.class);
                this.startActivity(newNote);
                break;

            default :

        }
        return super.onOptionsItemSelected(item);
    }



    /**   * When using the ActionBarDrawerToggle, you must call it during   * onPostCreate() and onConfigurationChanged()...   */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the ic_drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void startHelpDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        alertView = inflater.inflate(R.layout.help_dialog, null);

        builder.setView(alertView);


        builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Add new Channel

            }
        });


        myDialog = builder.create();
        myDialog.show();
    }

    public void startAboutDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        alertView = inflater.inflate(R.layout.about_dialog, null);

        builder.setView(alertView);


        builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Add new Channel

            }
        });


        myDialog = builder.create();
        myDialog.show();
    }
}
