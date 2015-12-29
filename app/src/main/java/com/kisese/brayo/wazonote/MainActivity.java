package com.kisese.brayo.wazonote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import storage.AndroidExplorer;
import storage.MyListAdapter;
import storage.RegistrationAdapter;
import storage.RegistrationOpenHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    AlertDialog myDialog;
    View alertView;
    private ListView notes_list;
    int[] to = { R.id.linksy, R.id.headeline};
    RegistrationAdapter adapter_ob;
    RegistrationOpenHelper helper_ob;
    String[] from = { helper_ob.LINK, helper_ob.HEADLINE};
    SQLiteDatabase db_ob;
    ListView nameList;
    Cursor cursor;
    private SharedPreferences notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        adapter_ob = new RegistrationAdapter(this);
        notes_list = (ListView)findViewById(R.id.notes_list);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        cursor = adapter_ob.queryName();
        MyListAdapter materials = new MyListAdapter(this, R.layout.row, cursor, from, to);
        notes_list.setAdapter(materials);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            Intent addNote = new Intent(this, AddNoteActivity.class);
            this.startActivity(addNote);
        } else if (id == R.id.nav_doodle) {
            Intent addDoodle = new Intent(this, Doodlz.class);
            this.startActivity(addDoodle);
        } else if (id == R.id.nav_my_doodles) {
            Intent myDoodles = new Intent(this, MyDoodlesActivity.class);
            this.startActivity(myDoodles);
        } else if (id == R.id.nav_sticky) {
            Intent stickyNote = new Intent(this, StickyNoteActivity.class);
            this.startActivity(stickyNote);
        } else if (id == R.id.nav_secure) {
            Intent secureNote = new Intent(this, SecureActivity.class);
            this.startActivity(secureNote);
        } else if (id == R.id.nav_sync) {
            Intent backups = new Intent(this, AndroidExplorer.class);
            this.startActivity(backups);
        }else if (id == R.id.nav_help) {
            startHelpDialog();
        }else if (id == R.id.nav_about) {
            startAboutDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
