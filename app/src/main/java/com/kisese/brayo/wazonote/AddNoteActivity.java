package com.kisese.brayo.wazonote;

/**
 * Created by Brayo on 7/23/2014.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.BreakIterator;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import storage.MainActivityList;
import storage.RegistrationActivity;
import storage.RegistrationAdapter;

public class AddNoteActivity  extends ActionBarActivity {

    RegistrationAdapter adapter;
    Button back;
    EditText title, story;
    String date;
    String title_text;
    String story_text;
    private TextView text;
    private View layout;
    String check;
    String a;
    String temp;

    private SensorManager sensorManager; // monitors accelerometer
    private float acceleration; // acceleration
    private float currentAcceleration; // current acceleration
    private float lastAcceleration; // last acceleration

    // value used to determine whether user shook the device to erase
    private static final int ACCELERATION_THRESHOLD = 105000;


    private SharedPreferences notes;
    private String LOG_TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);

        title = (EditText)findViewById(R.id.note_title);
        story = (EditText)findViewById(R.id.note_text);

        //set shared preferences variable
        notes = getSharedPreferences("notes_status", Context.MODE_PRIVATE);


        // initialize acceleration values
        acceleration = 0.00f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;

        enableAccelerometerListening(); // listen for shake


        LayoutInflater inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        text = (TextView) layout.findViewById(R.id.text);


        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int time = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);
        int am = c.get(Calendar.AM_PM);

        if(am == 1){
            a = "PM";
        }else{
            a = "AM";
        }

        if(min < 10){
            date = day + "/ " + month + "/ " + year + " - " + time + " : 0" + min + " " + a;
        }else {
            date = day + "/ " + month + "/ " + year + " - " + time + " : " + min  + " " + a;
        }


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F08080")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.add_note) + "</font"));
        getSupportActionBar().getThemedContext();
        //getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    // when app is sent to the background, stop listening for sensor events
    @Override
    protected void onPause(){
        super.onPause();
        disableAccelerometerListening(); // don't listen for shake
    } // end method onPause

    @Override
    protected void onStop() {
        super.onStop();
        disableAccelerometerListening(); // don't listen for shake

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        enableAccelerometerListening();
    }

    // enable listening for accelerometer events
    private void enableAccelerometerListening(){
        // initialize the SensorManager
        sensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void disableAccelerometerListening()
    {
        // stop listening for sensor events
        if (sensorManager != null)
        {
            sensorManager.unregisterListener(
                    sensorEventListener,
                    sensorManager.getDefaultSensor(
                            Sensor.TYPE_ACCELEROMETER));
            sensorManager = null;
        } // end if
    } // end method disableAccelerometerListening


    // event handler for accelerometer events
    private SensorEventListener sensorEventListener =
            new SensorEventListener()
            {
                // use accelerometer to determine whether user shook device
                @Override
                public void onSensorChanged(SensorEvent event)
                {

                        // get x, y, and z values for the SensorEvent
                        float x = event.values[0];
                        float y = event.values[1];
                        float z = event.values[2];

                        // save previous acceleration value
                        lastAcceleration = currentAcceleration;

                        // calculate the current acceleration
                        currentAcceleration = x * x + y * y + z * z;

                        // calculate the change in acceleration
                        acceleration = currentAcceleration *
                                (currentAcceleration - lastAcceleration);

                        // if the acceleration is above a certain threshold
                        if (acceleration > ACCELERATION_THRESHOLD)
                        {
                           // save note

                            if(title.getText().toString().isEmpty() && story.getText().toString().isEmpty()){

                            }else {
                                addNote();
                            }


                    } // end if
                } // end method onSensorChanged

                // required method of interface SensorEventListener
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy)
                {
                } // end method onAccuracyChanged
            }; // end anonymous inner class


    public void addNote(){
        adapter = new RegistrationAdapter(this);

        SharedPreferences.Editor preferencesEditor = notes.edit();
        preferencesEditor.putString("notes_empty", "no");
        preferencesEditor.commit();

        title_text = title.getText().toString();
        story_text = story.getText().toString();

        if(story_text.length()==0 && title_text.length()>0){

            story_text = title.getText().toString();

            String title;
            title = story_text.substring(0, Math.min(story_text.length(),30));

            String temp = "";
            temp = title_text;

            if(temp != "")
                adapter.insertDetails(date, title, story_text);
            else
                adapter.insertDetails(date, title.concat("_b"), story_text);

            // Toast.makeText(getApplicationContext(), Long.toString(val),
            // 300).show();
            showToast("Note Saved");
            writeToFile();
            Intent intent = new Intent(AddNoteActivity.this, MainMenuActivity.class);
            AddNoteActivity.this.startActivity(intent);
            finish();


        }else if(title_text.length()==0 && story_text.length()>0){
            //showToast("Please add a title");
            title_text = story.getText().toString();

            title_text = title_text.substring(0, Math.min(title_text.length(),30));

            String temp = "";
            temp = title_text;

            if(temp != "")
                adapter.insertDetails(date, title_text, story_text);
            else
                adapter.insertDetails(date, title_text.concat("_b"), story_text);

            // Toast.makeText(getApplicationContext(), Long.toString(val),
            // 300).show();
            showToast("Note Saved");
            writeToFile();
            Intent intent = new Intent(AddNoteActivity.this, MainMenuActivity.class);
            AddNoteActivity.this.startActivity(intent);
            finish();
        }else if(title_text.length()>0 && story_text.length()>0){
            String temp = "";
            temp = title_text;

            title_text = title_text.substring(0, Math.min(title_text.length(),30));

            if(temp != "")
                adapter.insertDetails(date, title_text, story_text);
            else
                adapter.insertDetails(date, title_text.concat("_b"), story_text);

            // Toast.makeText(getApplicationContext(), Long.toString(val),
            // 300).show();
            showToast("Note Saved");
            writeToFile();
            Intent intent = new Intent(AddNoteActivity.this, MainMenuActivity.class);
            AddNoteActivity.this.startActivity(intent);
            finish();
        }else{

        }
disableAccelerometerListening();
    }



    //backup files to external storage

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    public void writeToFile(){
        if (isExternalStorageWritable() == true && isExternalStorageReadable() == true) {
            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            String head = title.getText().toString();
            String body = story.getText().toString();

            File myDir = new File(root + "/wazo_note_backups");
            myDir.mkdirs();

            File file = new File(myDir, head.concat(".txt"));

            if (file.exists())
                file.delete();

            try {
                FileOutputStream out = new FileOutputStream(file);
                PrintWriter pw = new PrintWriter(out);

                pw.println(body);

                pw.flush();
                pw.close();
                out.close();

               // showToast("Backup Successful");
                //  Toast.makeText(this, "Backup Succesful", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //End of backup files



    @Override
    public void onBackPressed() {
            addNote();

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_main, menu);
        return true;
    }

    public void showToast(String story_text){
        text.setText(story_text);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case android.R.id.home:
                addNote();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
